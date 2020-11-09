import java.sql.*;  
public class SqlRequest{
	private Connection con;

	private final String database;
	private final String username;
	private final String password;
	private final int port;
	private final String address;

	public static final String defaultAddress = "localhost";
	public static final int defaultPort = 3306;

	public SqlRequest(String database, String username, String password, String address, int port){
		this.database = database;
		this.username=username;
		this.password=password;
		this.address=address;
		this.port=port;
	}

	public SqlRequest(String database, String username, String password){
		this(database,username,password,defaultAddress,defaultPort);
	}

	public boolean connect(){
		try{
		Class.forName("com.mysql.jdbc.Driver");  
		con=DriverManager.getConnection(  
			"jdbc:mysql://"+address+":"+port+"/"+database,username,password);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return false;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean testSelect(String table){
		try{
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from "+table);  
			while(rs.next())  
				System.out.println(rs.getLong("id_utilisateur")+"  "+rs.getString("mail")); 
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ResultSet executeQuery(String query){
		try{
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery(query);
			return rs;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public int executeUpdate(String query){
		try{
			Statement stmt=con.createStatement();
			System.out.println(query); 
			return stmt.executeUpdate(query);
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	public boolean close(){
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void finalize(){
		close();
	}
}  