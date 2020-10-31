import java.sql.*;  
import java.util.ArrayList;

public class Edition extends DataTable{
	private long isbn;
	private String editor;
	private int year;
	private long id_oeuvre;
	private Oeuvre oeuvre;

	private static final String basicSelect = "Select * from edition";

	private Edition(ResultSet rs){
		try{
			isbn = rs.getInt("ISBN");
			editor = rs.getString("editeur");
			year = rs.getInt("annee");
			id_oeuvre = rs.getInt("id_oeuvre");

		}catch(SQLException e ){
			e.printStackTrace();
			// XXX a revoir
		}
	}

	public Edition(){}

	public void initFields(SqlRequest sqlRequest)throws SQLException{
		oeuvre = Oeuvre.getOeuvreById(id_oeuvre,sqlRequest);
	}

	//Getters
	public long getIsbn(){return isbn;}
	public String getEditor(){return editor;}
	public int getYear(){return year;}
	public Oeuvre getOeuvre(){return oeuvre;}
	public long getIdoeuvre(){return id_oeuvre;}
	public String getAuthors(){return oeuvre.getAuthors();}

	//Setters
	public void setEdition(long isbn,String editor, int year, Oeuvre oeuvre){
		this.isbn = isbn;
		this.editor = editor;
		this.year = year;
		this.oeuvre = oeuvre;
		this.id_oeuvre = oeuvre.getId();
	}


	//Récupération de la liste complete des editions

	public static ArrayList<Edition> getListEdition(SqlRequest sqlRequest){
		ArrayList<Edition> res = new ArrayList<Edition>();
		String query = basicSelect;
		try{
			ResultSet rs = sqlRequest.executeQuery(query);
			while(rs.next()){
				Edition tmp = new Edition(rs);
				tmp.initFields(sqlRequest);
				res.add(tmp);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}



	//Recupération d'une edition

	//Via ID
	public static Edition getEditionById(long id, SqlRequest sqlRequest)throws SQLException{
		ResultSet rs = getById(id,sqlRequest);
		if(!rs.next())
			return null;
		Edition tmp =  new Edition(rs);
		tmp.initFields(sqlRequest);
		return tmp;
	}

	private static ResultSet getById(long id, SqlRequest sqlRequest){
		String query = basicSelect+" where ISBN ="+id;
		return sqlRequest.executeQuery(query);
	}


	//Ajouter une edition
	public int insertValue(SqlRequest sqlRequest){
		int tmp = isValideIsbn(sqlRequest, isbn);
		if(tmp<0) return tmp;

		String query = String.format("Insert into edition(ISBN, editeur, annee, id_oeuvre) values ('%d', '%s', '%d', '%d')",
			isbn, editor, year, id_oeuvre);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	//Modifier une edition
	public int updateValue(SqlRequest sqlRequest){
		String query = String.format("UPDATE edition SET editeur = '%s', annee = '%d', id_oeuvre = '%d' where isbn = %d",
			editor, year,id_oeuvre, isbn);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	public static int isValideIsbn(SqlRequest sqlRequest, long isbn){
		try{
		if(getEditionById(isbn,sqlRequest) != null)
			return -11;
		}catch(SQLException e){
			return -999;
		}

		return 0;
	}


	public String toString(){
		return String.format("%s %d",editor,year);
	}

	public boolean equals(Edition item){
		return item.isbn==isbn;
	}



}