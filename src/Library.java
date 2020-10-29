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
		if(connectedUser.getIdCategorie() == 1) // Possiblement a changer XXX
			admin=true;
		return true;
	}

	public void disconnct(){
		connectedUser = null;
		admin=false;
	}

	// Utilisateurs 
	public ArrayList<User> getUsers(){
		return User.getListUsers(mainDatabase);
	}

	// -1 : le mail est déja utilisé
	// -2 : other error
	public int addUser(String name, String surename, String mail, String password,long id_category) throws SQLException{
		if(User.getUserByMail(mail,mainDatabase) != null)
			return -1;

		int tmp = User.insertValue(mainDatabase, name, surename, mail, password, id_category);
		if(tmp <0)
			return -2;
		return tmp;
	}




}