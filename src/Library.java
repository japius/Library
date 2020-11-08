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

	public User getConnectedUser(){
		return connectedUser;
	}

	public boolean connect(String mail, String password){
		System.out.println("Try to connect :"+ mail +" , "+password);
		try{
			User user = User.getUserByMail(mail, mainDatabase);
			if(user == null)
				return false;
			if(!user.comparePass(password))
				return false;
			connectedUser = user;
			if(connectedUser.getCategory() == 1) // Possiblement a changer XXX
				admin=true;
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	public void disconnect(){
		connectedUser = null;
		admin=false;
	}


	// -1 : adresse mail invalide
	// -2 : le mail est déja utilisé
	// -3 : autre erreur
	public int updateData(DataTable data){
		System.out.println("updateData");
		return data.updateValue(mainDatabase);
	}


	public int insertData(DataTable data){
		System.out.println("insertData");
		return data.insertValue(mainDatabase);
	}


	public int addAuthor(long id_author, long id_oeuvre){
		return Oeuvre.addAuthor(mainDatabase, id_author, id_oeuvre);
	}

	public int borrowBook(Book book, User user){
		return book.borrowBook(mainDatabase,user);
	}

	public int returnBook(Book book){
		return book.returnBook(mainDatabase);
	}


	// Utilisateurs 
	public ArrayList<User> getUsers(){
		return User.getListUser(mainDatabase);
	}


	// Categories
	public ArrayList<Category> getCategories(){
		return Category.getListCategory(mainDatabase);
	}


	// Authors
	public ArrayList<Author> getAuthors(){
		return Author.getListAuthor(mainDatabase);
	}

	public ArrayList<Author> getAuthors(long id_oeuvre){
		return Author.getListAuthor(mainDatabase,id_oeuvre);
	}


	// Oeuvres
	public ArrayList<Oeuvre> getOeuvres(){
		return Oeuvre.getListOeuvre(mainDatabase);
	}

	public ArrayList<Oeuvre> getOeuvres(long id_author){
		return Oeuvre.getListOeuvre(mainDatabase,id_author);
	}

	// Editions
	public ArrayList<Edition> getEditions(){
		return Edition.getListEdition(mainDatabase);
	}

	// Books

	public ArrayList<Book> getBooks(){
		return Book.getListBook(mainDatabase);
	}

	public ArrayList<Book> getBooks(long id_user){
		return Book.getListBook(mainDatabase,id_user);
	}

	// Liste rouge
	public ArrayList<RedList> getRedLists(){
		return RedList.getListRedList(mainDatabase);
	}

	public ArrayList<RedList> getRedLists(long id_user){
		return RedList.getListRedList(mainDatabase, id_user);
	}

	public ArrayList<RedList> getActualRedLists(){
		return RedList.getActualRedList(mainDatabase);
	}

}