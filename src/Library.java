import java.sql.*; 

import java.util.ArrayList;

public class Library{

	private User connectedUser = null;
	private boolean admin = false;
	private SqlRequest mainDatabase;


	public Library(){
		System.out.println("Lancement de la bibliotheque");
		mainDatabase = new SqlRequest("library","mejane","password");
		mainDatabase.connect();
	}

	public void close(){
		mainDatabase.close();
	}


	public boolean isConnect(){
		return connectedUser != null;
	}


	public boolean isAdmin(){
		return admin;
	}

	public boolean connect(String mail, String password)throws SQLException{
		User user = User.getUserByMail(mail, mainDatabase);
		if(user == null)
			return false;
		if(!user.comparePass(password))
			return false;
		connectedUser = user;
		if(connectedUser.getCategory() == 1) // Possiblement a changer XXX
			admin=true;
		return true;
	}

	public void disconnct(){
		connectedUser = null;
		admin=false;
	}


	// -1 : adresse mail invalide
	// -2 : le mail est déja utilisé
	// -3 : autre erreur
	public int updateData(DataTable data){
		System.out.println("je rnetre dans updateData");
		return data.updateValue(mainDatabase);
	}


	// Utilisateurs 
	public ArrayList<User> getUsers(){
		return User.getListUser(mainDatabase);
	}

	// -1 : adresse mail invalide
	// -2 : le mail est déja utilisé
	// -3 : autre erreur
	public int addUser(String name, String surename, String mail, String password,long id_category) throws SQLException{

		int tmp = User.insertValue(mainDatabase, name, surename, mail, password, id_category);
		if(tmp <0)
			return -2;
		return tmp;
	}


	// Categories
	public ArrayList<Category> getCategories(){
		return Category.getListCategory(mainDatabase);
	}

	// -2 : other error
	public int addCategory(String name, int borrowing, int time) throws SQLException{
		int tmp = Category.insertValue(mainDatabase, name, borrowing, time);
		return tmp;
	}


	// Authors

	public ArrayList<Author> getAuthors(){
		return Author.getListAuthor(mainDatabase);
	}

}