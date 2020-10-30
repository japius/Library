import java.sql.*;  

public abstract class DataTable{
	

	public static String errorInsert(int errorValue){
		switch(errorValue){
			case 0 : 
			return "Aucune erreur.";

			case -1 :
			return "Email invalide.";

			case -2 :
			return "Adresse email deja utilise.";

			case -11 : 
			return "Numero ISBN deja pris.";


			case -999 :
			return "Erreur dans la base de donees.";

			default :
			return "Unknown error";

		}
	}

	public abstract int updateValue(SqlRequest sqlRequest);

	public abstract int insertValue(SqlRequest sqlRequest);
}