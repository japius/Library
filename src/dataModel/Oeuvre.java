import java.sql.*;  
import java.util.ArrayList;

public class Oeuvre extends DataTable{
	private long id = 0;
	private String title = null;
	private int year = 0;
	private long id_auth = 0;
	private Author author = null;

	private static final String basicSelect = "Select * from oeuvre";

	private Oeuvre(ResultSet rs){
		try{
			id = rs.getInt("id_oeuvre");
			title = rs.getString("titre");
			year = rs.getInt("annee");
			id_auth = rs.getInt("id_auteur");

		}catch(SQLException e ){
			e.printStackTrace();
			// XXX a revoir
		}
	}

	public Oeuvre(){}

	public void initFields(SqlRequest sqlRequest)throws SQLException{
		author = Author.getAuthorById(id_auth,sqlRequest);
	}

	//Getters
	public long getId(){return id;}
	public String getTitle(){return title;}
	public int getYear(){return year;}
	public String getFirstname(){return author.getFirstname();}
	public String getLastname(){return author.getLastname();}
	public int getAuthyear(){return author.getYear();}
	public Author getAuthor(){return author;}

	//Setters
	public void setOeuvre(String title, int year,Author author){
		this.title = title;
		this.year = year;
		this.author = author;
		this.id_auth = author.getId();
	}


	//Récupération de la liste complete des oeuvres

	public static ArrayList<Oeuvre> getListOeuvre(SqlRequest sqlRequest){
		ArrayList<Oeuvre> res = new ArrayList<Oeuvre>();
		String query = basicSelect;
		try{
			ResultSet rs = sqlRequest.executeQuery(query);
			while(rs.next()){
				Oeuvre tmp = new Oeuvre(rs);
				tmp.initFields(sqlRequest);
				res.add(tmp);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}



	//Recupération d'une oeuvre

	//Via ID
	public static Oeuvre getOeuvreById(long id, SqlRequest sqlRequest)throws SQLException{
		ResultSet rs = getById(id,sqlRequest);
		if(!rs.next())
			return null;
		Oeuvre tmp =  new Oeuvre(rs);
		tmp.initFields(sqlRequest);
		return tmp;
	}

	private static ResultSet getById(long id, SqlRequest sqlRequest){
		String query = basicSelect+" where id_oeuvre="+id;
		return sqlRequest.executeQuery(query);
	}


	//Ajouter une oeuvre
	public int insertValue(SqlRequest sqlRequest){
		String query = String.format("Insert into oeuvre(titre, annee, id_auteur) values ( '%s', '%d', '%d')",
			title, year, id_auth);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	//Modifier une oeuvre
	public int updateValue(SqlRequest sqlRequest){
		if(id <= 0)
			return -1;

		String query = String.format("UPDATE oeuvre SET titre = '%s', annee = '%d', id_auteur = '%d' where id_oeuvre = %d",
			title, year,id_auth, id);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	public String toString(){
		return String.format("%s (%d)",title,year);
	}

	public boolean equals(Oeuvre item){
		return item.id==id;
	}



}