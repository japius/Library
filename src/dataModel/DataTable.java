import java.sql.*;  

public abstract class DataTable{
	

	public static String errorInsert(int errorValue){
		switch(errorValue){
			case 0 : 
			return "Aucune erreur.";

			case -1 :
			return "Email invalide";

			case -2 :
			return "Adresse email deja utilise";

			default :
			return "Unknown error";

		}
	}

	public abstract int updateValue(SqlRequest sqlRequest);

	public abstract int insertValue(SqlRequest sqlRequest);
}