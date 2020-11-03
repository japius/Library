import java.sql.*;  
import java.util.ArrayList;
import java.util.Date;

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
	public Date getBegin(){return begin_date;}
	public Date getEnd(){return end_date;}

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
		/*int tmp = areValideDate();
		if(tmp < 0)
			return tmp;*/

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


	//Modifier une edition
	public int updateValue(SqlRequest sqlRequest){
		int tmp = areValideDate(sqlRequest);
		if(tmp < 0) return tmp;

		String query = String.format("UPDATE livre SET ISBN = '%d' where id_livre = %d",
			isbn,id);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	public static int areValideDate(SqlRequest sqlRequest){
		return 0;
	}


	public String toString(){
		return String.format("%s",edition.toString());
	}

	public boolean equals(Book item){
		return item.id==id;
	}



}