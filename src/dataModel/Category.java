import java.sql.*;  
import java.util.ArrayList;

public class Category implements DataTable{
	private long id;
	private String name;
	private int borrowing;
	private int time;

	private static final String basicSelect = "Select * from categorie";

	private Category(ResultSet rs){
		try{
			id = rs.getInt("id_categorie");
			name = rs.getString("nom");
			borrowing = rs.getInt("nombre_emprunt");
			time = rs.getInt("duree");

		}catch(SQLException e ){
			e.printStackTrace();
			// XXX a revoir
		}
	}

	//Getters
	public long getId(){return id;}
	public String getName(){return name;}
	public int getBorrowing(){return borrowing;}
	public int getTime(){return time;}

	//Setters
	public void setCategory(String name, int time, int borrowing){
		this.name = name;
		this.time = time;
		this.borrowing = borrowing;
	}


	//Récupération de la liste complete des categories

	public static ArrayList<Category> getListCategory(SqlRequest sqlRequest){
		ArrayList<Category> res = new ArrayList<Category>();
		String query = basicSelect;
		try{
			ResultSet rs = sqlRequest.executeQuery(query);
			while(rs.next()){
				res.add(new Category(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}



	//Recupération d'une categorie

	//Via ID
	public static Category getCategoryById(long id, SqlRequest sqlRequest)throws SQLException{
		ResultSet rs = getById(id,sqlRequest);
		rs.next();
		return new Category(rs);
	}

	private static ResultSet getById(long id, SqlRequest sqlRequest){
		String query = basicSelect+" where id_categorie="+id;
		return sqlRequest.executeQuery(query);
	}


	//Ajouter une categorie
	public static int insertValue(SqlRequest sqlRequest, String name, int borrowing, int time){
		String query = String.format("Insert into categorie(nom, nombre_emprunt, duree) values ( '%s', '%d', '%d')",
				name, borrowing, time);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -2;
		return res;
	}


	//Modifier une categorie
	public int updateValue(SqlRequest sqlRequest){
		if(id <= 0)
			return -1;

		String query = String.format("UPDATE categorie SET nom = '%s', duree = '%d', nombre_emprunt = '%d' where id_categorie = %d",
			name, time, borrowing, id);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -2;
		return res;
	}


	public String toString(){
		return String.format("%d - %s ( duree : %d, nombre emprunt : %d)",id,name,time,borrowing);
	}

	public boolean equals(Category cat){
		return cat.id==id;
	}



}