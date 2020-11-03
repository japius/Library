import java.sql.*;  
import java.util.ArrayList;

public class Author extends DataTable{
	private long id = 0;
	private String firstname = null;
	private String lastname = null;
	private int year = 0;

	private static final String basicSelect = "Select * from auteur";

	private Author(ResultSet rs){
		try{
			id = rs.getInt("id_auteur");
			firstname = rs.getString("nom");
			lastname = rs.getString("prenom");
			year = rs.getInt("annee");

		}catch(SQLException e ){
			e.printStackTrace();
			// XXX a revoir
		}
	}

	public Author(){
	}

	//Getters
	public long getId(){return id;}
	public String getFirstname(){return firstname;}
	public String getLastname(){return lastname;}
	public int getYear(){return year;}

	//Setters
	public void setAuthor(String firstname, String lastname, int year){
		this.firstname = firstname.toLowerCase();
		this.lastname = lastname.toUpperCase();
		this.year = year;
	}


	//Récupération de la liste complete des auteurs

	private static ArrayList<Author> getListAuthor(SqlRequest sqlRequest,String query){
		ArrayList<Author> res = new ArrayList<Author>();
		try{
			ResultSet rs = sqlRequest.executeQuery(query);
			while(rs.next()){
				res.add(new Author(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}

	public static ArrayList<Author> getListAuthor(SqlRequest sqlRequest){
		return getListAuthor(sqlRequest,basicSelect);
	}

	public static ArrayList<Author> getListAuthor(SqlRequest sqlRequest, long id_oeuvre){
		return getListAuthor(sqlRequest, basicSelect + " NATURAL JOIN a_ecrit where id_oeuvre = "+id_oeuvre);
	}



	//Recupération d'un auteur

	//Via ID
	public static Author getAuthorById(long id, SqlRequest sqlRequest)throws SQLException{
		ResultSet rs = getById(id,sqlRequest);
		if(!rs.next())
			return null;
		return new Author(rs);
	}

	private static ResultSet getById(long id, SqlRequest sqlRequest){
		String query = basicSelect+" where id_auteur="+id;
		return sqlRequest.executeQuery(query);
	}


	//Ajouter un auteur
	public int insertValue(SqlRequest sqlRequest){
		String query = String.format("Insert into auteur(nom, prenom, annee) values ( '%s', '%s', '%d')",
			lastname, firstname, year);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	//Modifier une categorie
	public int updateValue(SqlRequest sqlRequest){
		if(id <= 0)
			return -1;

		String query = String.format("UPDATE auteur SET nom = '%s', prenom = '%s', annee = '%d' where id_auteur = %d",
			lastname, firstname, year, id);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	public String toString(){
		return String.format("%s %s",lastname,firstname,year);
	}

	public boolean equals(Author item){
		return item.id==id;
	}



}