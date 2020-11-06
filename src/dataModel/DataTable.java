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

			case -21 :
			return "La quantite de livre a ajouter doit etre supérieur a 1.";

			case -31 :
			return "L'auteur en question n'existe pas dans la base de données.";

			case -32 :
			return "L'oeuvre en question n'existe pas dans la base de données.";


			case -999 :
			return "Erreur dans la base de donees.";

			default :
			return "Unknown error";

		}
	}

	public abstract int updateValue(SqlRequest sqlRequest);

	public abstract int insertValue(SqlRequest sqlRequest);
}