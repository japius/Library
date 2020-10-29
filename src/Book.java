import java.sql.*; 
import java.util.ArrayList;

public class Book implements DataTable{
	private long id_book;
	private long isbn;
	private String edition;
	private int year_edition;
	private String title;
	private long id_oeuvre;
	private int year_oeuvre;

	private static final String basicSelect = "Select oeuvre.id_oeuvre, titre, edition.ISBN as ISBN, oeuvre.annee as oeuvre_annee, editeur, edition.annee as editeur_annee, id_livre from oeuvre, edition, livre where oeuvre.id_oeuvre = edition.id_oeuvre and edition.ISBN = livre.ISBN";


	private Book(ResultSet rs){
		try{
			id_book = rs.getInt("id_livre");
			isbn = rs.getInt("ISBN");
			edition = rs.getString("editeur");
			year_edition = rs.getInt("editeur_annee");
			title=rs.getString("titre");
			id_oeuvre = rs.getInt("id_oeuvre");
			year_oeuvre = rs.getInt("oeuvre_annee");
		}catch(SQLException e ){
			e.printStackTrace();
			// XXX a revoir
		}
	}

	public static Book getBookById(SqlRequest sqlRequest, long id) throws SQLException{
		String query = basicSelect+" and id_livre = "+id;
		ResultSet rs = sqlRequest.executeQuery(query);
		rs.next();
		return new Book(rs);
	}

	public static ArrayList<Book> getAllBooks(SqlRequest sqlRequest){
		ArrayList<Book> res = new ArrayList<Book>();
		String query = basicSelect;
		try{
			ResultSet rs = sqlRequest.executeQuery(query);
			while(rs.next()){
				System.out.println("Ajout d'une oeuvre");
				res.add(new Book(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;	
	}

	//Ajouter un livre

	//  -----------   Partie sur Oeuvre
	public static ResultSet getOeuvre(SqlRequest sqlRequest, long id){
		String query = "Select * from oeuvre where id_oeuvre="+id;
		ResultSet rs = sqlRequest.executeQuery(query);
		return rs;
	}

	public static ResultSet getOeuvre(SqlRequest sqlRequest, String title, int year){
		String query = String.format("Select * from oeuvre where titre = '%s' and annee = %d",title,year);
		ResultSet rs = sqlRequest.executeQuery(query);
		return rs;
	}

	public static boolean addOeuvre(SqlRequest sqlRequest, String title, int year){
		String query = String.format("Insert into oeuvre(titre,annee) values ('%s', %d)",title,year);
		int res = sqlRequest.executeUpdate(query);
		return res >= 0;
	}

	//  -----------   Partie sur Edition
	public static ResultSet getEdition(SqlRequest sqlRequest, long id){
		String query = "Select * from edition where id_edition="+id;
		ResultSet rs = sqlRequest.executeQuery(query);
		return rs;
	}

	public static boolean addEdition(SqlRequest sqlRequest, long isbn, String edition, int year, long id_oeuvre){
		String query = String.format("Insert into edition(ISBN,editeur,annee,id_oeuvre) values (%d,'%s', %d, %d)",isbn,edition,year,id_oeuvre);
		int res = sqlRequest.executeUpdate(query);
		return res >= 0;
	}

	//  -----------   Partie sur Livre
	public static ResultSet getBook(SqlRequest sqlRequest, long id){
		String query = "Select * from livre where id_livre="+id;
		ResultSet rs = sqlRequest.executeQuery(query);
		return rs;
	}

	public static boolean addBook(SqlRequest sqlRequest, long isbn){
		String query = String.format("Insert into livre(ISBN) values (%d)",isbn);
		int res = sqlRequest.executeUpdate(query);
		return res >= 0;
	}

	//Modifier un livre
	public int updateValue(SqlRequest sqlRequest){
		//XXX A modifier
		return 0;
	}


	// print de l'utilisateur
	public void print(){

		System.out.println("id_book = "+id_book);
		System.out.println("isbn = "+isbn);
		System.out.println("edition = "+ edition);
		System.out.println("year_edition = "+ year_edition);
		System.out.println("title = "+ title);
		System.out.println("id_oeuvre = "+ id_oeuvre);
		System.out.println("year_oeuvre = "+ year_oeuvre);
	}

}