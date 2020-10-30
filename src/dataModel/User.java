import java.sql.*;  
import java.util.ArrayList;
import java.util.regex.*;

public class User extends DataTable{
	private long id;
	private String name;
	private String surename;
	private String mail;
	private String password;
	private long category;

	private static final String basicSelect = "Select * from utilisateur";

	private User(ResultSet rs){
		try{
			id = rs.getInt("id_utilisateur");
			name = rs.getString("prenom");
			surename = rs.getString("nom");
			mail=rs.getString("mail");
			password = rs.getString("password");
			category = rs.getInt("id_categorie");
		}catch(SQLException e ){
			e.printStackTrace();
			// XXX a revoir
		}
	}

	public User(){}

	//Getters

	public long getId(){return id;}
	public String getName(){return name;}
	public String getSurename(){return surename;}
	public String getMail(){return mail;}
	public long getCategory(){return category;}


	//Setters
	public void setUser(String name, String surename,String mail, long category){
		this.name=name;
		this.surename=surename;
		this.mail=mail;
		this.category=category;
	}


	//Récupération de la liste complete des utilisateurs

	public static ArrayList<User> getListUser(SqlRequest sqlRequest){
		ArrayList<User> res = new ArrayList<User>();
		String query = basicSelect;
		try{
			ResultSet rs = sqlRequest.executeQuery(query);
			while(rs.next()){
				res.add(new User(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}


	
	//Recupération d'un utilisateur

	//Via ID
	public static User getUserById(long id, SqlRequest sqlRequest)throws SQLException{
		ResultSet rs = getById(id,sqlRequest);
		rs.next();
		return new User(rs);
	}

	private static ResultSet getById(long id, SqlRequest sqlRequest){
		String query = basicSelect+" where id_utilisateur="+id;
		return sqlRequest.executeQuery(query);
	}

	//Via Mail
	public static User getUserByMail(String mail, SqlRequest sqlRequest)throws SQLException{
		ResultSet rs = getByMail(mail,sqlRequest);
		if(!rs.next())
			return null;
		return new User(rs);
	}


	private static ResultSet getByMail(String mail,SqlRequest sqlRequest){
		String query = String.format(basicSelect+" where mail ='%s'",mail);
		return sqlRequest.executeQuery(query);
	}



	//Ajouter un utilisateur
	public int insertValue(SqlRequest sqlRequest){
		int tmp = isValideMail(sqlRequest, mail);
		if(tmp<0) return tmp;

		String query = String.format("Insert into utilisateur(nom, prenom, mail, password, id_categorie) values ( '%s', '%s','%s','%s', '%d')",
				surename, name,mail,encryptPass(password),category);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	//Modifier un utilisateur
	public int updateValue(SqlRequest sqlRequest){
		if(!isEmailAdress(mail)) return -1;

		String query = String.format("UPDATE utilisateur SET prenom = '%s', nom = '%s', mail = '%s', password = '%s', id_categorie = %d where id_utilisateur = %d",
			name, surename,mail,password,category,id);

		System.out.println(query);

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -2;
		return res;
	}



	// Fonction sur le mot de passe
	private static String encryptPass(String str){
		return str; //XXX A modifier
	}

	public boolean comparePass(String str){
		return this.password.equals(str);//XXX A modifier
	}


	public static int isValideMail(SqlRequest sqlRequest, String email){
		if(!isEmailAdress(email))
			return -1;
		try{
		if(User.getUserByMail(email,sqlRequest) != null)
			return -2;
		}catch(SQLException e){
			return -999;
		}

		return 0;
	}


	public static boolean isEmailAdress(String email){
		Pattern p = Pattern.compile( "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
		Matcher m = p.matcher(email.toUpperCase());
		return m.matches();
	}


	public String toString(){
		return String.format("%d - %s %s",id,surename,name);
	}
}


