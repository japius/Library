import java.sql.*;  
import java.util.ArrayList;

public class Author extends DataTable{
	private long id;
	private String firstname;
	private String lastname;
	private int year;

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

	//Getters
	public long getId(){return id;}
	public String getFirstname(){return firstname;}
	public String getLastname(){return lastname;}
	public int getYear(){return year;}

	//Setters
	public void setAuthor(String firstname, String lastname, int year){
		this.firstname = firstname;
		this.lastname = lastname;
		this.year = year;
	}


	//Récupération de la liste complete des auteurs

	public static ArrayList<Author> getListAuthor(SqlRequest sqlRequest){
		ArrayList<Author> res = new ArrayList<Author>();
		String query = basicSelect;
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
	public static int insertValue(SqlRequest sqlRequest, String firstname, String lastname, int year){
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
		return String.format("%s %s, %d",lastname,firstname,year);
	}

	public boolean equals(Author item){
		return item.id==id;
	}



}