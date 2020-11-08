import java.sql.*;  
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class Book extends DataTable{
	private long id;
	private long isbn;
	private Edition edition;
	private long id_user;
	private User user;
	private Date begin_date;
	private Date end_date;
	private int quantity = 1;

	private static final String basicSelect = "Select * from livre";

	private Book(ResultSet rs){
		try{
			id = rs.getInt("id_livre");
			isbn = rs.getInt("isbn");
			try{
				begin_date = new Date(rs.getDate("date_debut").getTime());
				end_date = new Date(rs.getDate("date_fin").getTime());
			}catch(NullPointerException e){
				begin_date = null;
				end_date = null;
			}
			id_user = rs.getInt("id_utilisateur");

		}catch(SQLException e ){
			e.printStackTrace();
			// XXX a revoir
		}
	}

	public Book(){}

	public void initFields(SqlRequest sqlRequest)throws SQLException{
		edition = Edition.getEditionById(isbn,sqlRequest);
		if(id_user>0)
			user = User.getUserById(id_user,sqlRequest);
	}

	//Getters
	public long getIsbn(){return isbn;}
	public long getId(){return id;}
	public User getUser(){return user;}
	public Edition getEdition(){return edition;}
	public String getEditor(){return edition.getEditor();}
	public String getAuthors(){return edition.getAuthors();}
	public Oeuvre getOeuvre(){return edition.getOeuvre();}
	public String getBegin(){return dateSql(begin_date);}
	public String getEnd(){return dateSql(end_date);}
	public boolean getDispo(){return user==null;}

	//Setters
	public void setBook(Edition edition, User user, Date begin_date, Date end_date,int quantity){
		this.edition = edition;
		this.user=user;
		this.isbn = edition.getIsbn();
		this.begin_date = begin_date;
		this.end_date = end_date;
		if(user != null)
			this.id_user = user.getId();
		else
			this.id_user = -1;
		this.quantity = quantity;
	}


	//Récupération de la liste complete des livres

	public static ArrayList<Book> getListBook(SqlRequest sqlRequest, String query){
		ArrayList<Book> res = new ArrayList<Book>();
		try{
			ResultSet rs = sqlRequest.executeQuery(query);
			while(rs.next()){
				Book tmp = new Book(rs);
				tmp.initFields(sqlRequest);
				res.add(tmp);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}

	public static ArrayList<Book> getListBook(SqlRequest sqlRequest){
		return getListBook(sqlRequest, basicSelect);
	}

	public static ArrayList<Book> getListBook(SqlRequest sqlRequest,long id_user){
		return getListBook(sqlRequest, basicSelect+String.format(" where id_utilisateur = '%d'",id_user));
	}

	//Recupération d'un livre

	//Via ID
	public static Book getBookById(long id, SqlRequest sqlRequest)throws SQLException{
		ResultSet rs = getById(id,sqlRequest);
		if(!rs.next())
			return null;
		Book tmp =  new Book(rs);
		tmp.initFields(sqlRequest);
		return tmp;
	}

	private static ResultSet getById(long id, SqlRequest sqlRequest){
		String query = basicSelect+" where id_livre ="+id;
		return sqlRequest.executeQuery(query);
	}


	//Ajouter un livre
	public int insertValue(SqlRequest sqlRequest){
		if(quantity < 1){
			return -21;
		}

		String query = String.format("Insert into livre(ISBN) values ('%d')",
			isbn);

		for(int i = 0; i<quantity;i++){
			int res = sqlRequest.executeUpdate(query);
			if(res < 0) return -999;
		}
		return quantity;
	}


	public int borrowBook(SqlRequest sqlRequest, User user){
		Category cat;
		try{
			cat = Category.getCategoryById(user.getId(),sqlRequest);
		}catch(SQLException e){
			e.printStackTrace();
			return -999;
		}

		ArrayList<Book> books = getListBook(sqlRequest, user.getId());
		if(cat.getBorrowing()<=books.size())
			return -41;

		Date actualDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, cat.getTime());
		Date endDate = calendar.getTime();


		String query = String.format("Insert into est_emprunte values('%d','%d','%s')",getId(),user.getId(),dateSql(actualDate));
		sqlRequest.executeUpdate(query);
		System.out.println(query);

		query = String.format("UPDATE livre SET id_utilisateur = '%d', date_debut = '%s', date_fin = '%s' where id_livre = '%d'",user.getId(), dateSql(actualDate),dateSql(endDate),getId());
		System.out.println(query);
		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;

		return 0;
	}

	public int returnBook(SqlRequest sqlRequest){
		String query = String.format("UPDATE livre SET id_utilisateur = null, date_debut = null, date_fin = null where id_livre = '%d'",getId());
		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return 0;	
	}


	//Modifier un livre
	public int updateValue(SqlRequest sqlRequest){
		String query = String.format("UPDATE livre SET ISBN = '%d' where id_livre = %d",
			isbn,id);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}



	public String toString(){
		return String.format("%s",edition.toString());
	}

	public boolean equals(Book item){
		return item.id==id;
	}



}