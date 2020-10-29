import java.sql.*;  
import java.util.ArrayList;

public class User implements DataTable{
	private long id;
	private String name;
	private String surename;
	private String mail;
	private String password;
	private long id_category;

	private static final String basicSelect = "Select * from utilisateur";

	private User(ResultSet rs){
		try{
			id = rs.getInt("id_utilisateur");
			name = rs.getString("prenom");
			surename = rs.getString("nom");
			mail=rs.getString("mail");
			password = rs.getString("password");
			id_category = rs.getInt("id_categorie");
		}catch(SQLException e ){
			e.printStackTrace();
			// XXX a revoir
		}
	}

	//Getters

	public long getId(){return id;}

	public String getName(){return name;}
	public String getSurename(){return surename;}
	public String getMail(){return mail;}
	public long getIdCategorie(){return id_category;}


	//Récupération de la liste complete des utilisateurs

	public static ArrayList<User> getAllUsers(SqlRequest sqlRequest){
		ArrayList<User> res = new ArrayList<User>();
		String query = basicSelect;
		try{
			ResultSet rs = sqlRequest.executeQuery(query);
			while(rs.next()){
				System.out.println("Ajout User");
				res.add(new User(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}


	
	//Recupération d'un utilisateur

	public static User getUserByMail(String mail, SqlRequest sqlRequest)throws SQLException{
		ResultSet rs = getByMail(mail,sqlRequest);
		rs.next();
		return new User(rs);
	}

	public static User getUserById(long id, SqlRequest sqlRequest)throws SQLException{
		ResultSet rs = getById(id,sqlRequest);
		rs.next();
		return new User(rs);
	}

	private static ResultSet getById(long id, SqlRequest sqlRequest){
		String query = basicSelect+" where id_utilisateur="+id;
		return sqlRequest.executeQuery(query);
	}

	private static ResultSet getByMail(String mail,SqlRequest sqlRequest){
		String query = String.format(basicSelect+" where mail ='%s'",mail);
		return sqlRequest.executeQuery(query);
	}

	//Ajouter un utilisateur
	public static int insertValue(SqlRequest sqlRequest, String name, String surename, String mail, String password, long id_category){
		String query = String.format("Insert into utilisateur(nom, prenom, mail, password, id_categorie) values ( '%s', '%s','%s','%s', '%d')",
				name, surename,mail,password,id_category);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -2;
		return res;
	}


	//Modifier un utilisateur
	public int updateValue(SqlRequest sqlRequest){
		if(id <= 0)
			return -1;

		String query = String.format("UPDATE utilisateur SET prenom = '%s', nom = '%s', mail = '%s', password = '%s', id_categorie = %d",
			name, surename,mail,password,id_category);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -2;
		return res;
	}

	// Fonction sur le mot de passe
	private static String encryptPass(String str){
		return str; //XXX A modifier
	}

	private static boolean comparePass(String str1, String str2){
		return str1.equals(str2);//XXX A modifier
	}



	// print de l'utilisateur
	public void print(){
		System.out.println("id = "+id);
		System.out.println("name = "+name);
		System.out.println("surename = "+surename);
		System.out.println("email = "+mail);
	}
}