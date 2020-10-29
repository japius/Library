import java.sql.*;  

public interface DataTable{

	//private ResultSet getById(long id, SqlRequest sqlRequest);

	//public static int insertValue(SqlRequest sqlRequest);

	public default String errorInsert(int errorValue){
		switch(errorValue){
			case 0 : 
			return "No error in the insert";

			case -1 :
			return "Value already exists in database";

			case -2 :
			return "Error with database";

			default :
			return "Unknown error";

		}
	}

	public int updateValue(SqlRequest sqlRequest);
}