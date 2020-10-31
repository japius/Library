import java.sql.*;  
import java.util.ArrayList;

public class Oeuvre extends DataTable{
	private long id = 0;
	private String title = null;
	private int year = 0;
	private ArrayList<Author> authors;

	private static final String basicSelect = "Select * from oeuvre";

	private Oeuvre(ResultSet rs){
		try{
			id = rs.getInt("id_oeuvre");
			title = rs.getString("titre");
			year = rs.getInt("annee");

		}catch(SQLException e ){
			e.printStackTrace();
			// XXX a revoir
		}
	}

	public Oeuvre(){}

	public void initFields(SqlRequest sqlRequest)throws SQLException{
		authors = Author.getListAuthor(sqlRequest,id);
	}

	//Getters
	public long getId(){return id;}
	public String getTitle(){return title;}
	public int getYear(){return year;}

	public String getAuthors(){
		if(authors.size() == 0) return "";
		String res = authors.get(0).toString();
		for(int i = 1; i<authors.size();i++){
			res = res+", "+authors.get(i).toString();
		}

		return res;

	}

	//Setters
	public void setOeuvre(String title, int year){
		this.title = title;
		this.year = year;
	}


	//Récupération de la liste complete des oeuvres

	private static ArrayList<Oeuvre> getListOeuvre(SqlRequest sqlRequest,String query){
		ArrayList<Oeuvre> res = new ArrayList<Oeuvre>();
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

	public static ArrayList<Oeuvre> getListOeuvre(SqlRequest sqlRequest){
		return getListOeuvre(sqlRequest,basicSelect);
	}

	public static ArrayList<Oeuvre> getListOeuvre(SqlRequest sqlRequest, long id_author){
		return getListOeuvre(sqlRequest,basicSelect+ " NATURAL JOIN a_ecrit where id_auteur = "+id_author);
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
		String query = String.format("Insert into oeuvre(titre, annee) values ( '%s', '%d')",
			title, year);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	//Modifier une oeuvre
	public int updateValue(SqlRequest sqlRequest){
		if(id <= 0)
			return -1;

		String query = String.format("UPDATE oeuvre SET titre = '%s', annee = '%d' where id_oeuvre = %d",
			title, year, id);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	public String toString(){
		return String.format("%s",title,year);
	}

	public boolean equals(Oeuvre item){
		return item.id==id;
	}



}