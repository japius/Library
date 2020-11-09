import java.sql.*;  
import java.util.ArrayList;
import java.util.Date;

public class RedList extends DataTable{
	private long id_user;
	private Date begin_date;
	private Date end_date;
	private User user;

	private static final String basicSelect = "Select * from list_rouge";

	private RedList(ResultSet rs){
		try{
			id_user = rs.getLong("id_utilisateur");
			try{
				begin_date = new Date(rs.getDate("date_debut").getTime());
				end_date = new Date(rs.getDate("date_fin").getTime());
			}catch(NullPointerException e){
				begin_date = null;
				end_date = null;
			}
		}catch(SQLException e ){
			e.printStackTrace();
			// XXX a revoir
		}
	}

	public RedList(){}

	public void initFields(SqlRequest sqlRequest)throws SQLException{
		user = User.getUserById(id_user, sqlRequest);
	}

	//Getters
	public long getId(){return id_user;}
	public String getBegin(){return dateSql(begin_date);}
	public String getEnd(){return dateSql(end_date);}
	public String getUser(){return user.toString();}

	//Setters
	public void setRedList(Date begin, Date end){
		this.begin_date = begin;
		this.end_date = end;
	}

	public void setUser(User user){
		this.user = user;
		if(user!=null){
			id_user = user.getId();
		}
	}


	//Récupération de la liste rouge

	public static ArrayList<RedList> getListRedList(SqlRequest sqlRequest, String query){
		ArrayList<RedList> res = new ArrayList<RedList>();
		try{
			ResultSet rs = sqlRequest.executeQuery(query);
			while(rs.next()){
				RedList tmp = new RedList(rs);
				tmp.initFields(sqlRequest);
				res.add(tmp);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}

	public static ArrayList<RedList> getListRedList(SqlRequest sqlRequest){
		return getListRedList(sqlRequest, basicSelect);
	}

	public static ArrayList<RedList> getListRedList(SqlRequest sqlRequest,long id_user){
		return getListRedList(sqlRequest, basicSelect+String.format(" where id_utilisateur = '%d'",id_user));
	}

	public static ArrayList<RedList> getActualRedList(SqlRequest sqlRequest){
		Date date = new Date();
		return getListRedList(sqlRequest,basicSelect+String.format(" where date_fin < '%s'",dateSql(date)));
	}

	public static boolean isOnRedList(SqlRequest sqlRequest, long id_user){
		Date date = new Date();
		return getListRedList(sqlRequest,basicSelect+String.format(" where date_fin < '%s' and id_utilisateur = '%d'",dateSql(date),id_user)).size() != 0;
	}


	//Ajouter a la liste rouge
	public int insertValue(SqlRequest sqlRequest){
		String query = String.format("Insert into list_rouge(id_utilisateur,date_debut,date_fin) values ('%d', '%s', '%s')",
			id_user,dateSql(begin_date), dateSql(end_date));

		System.out.println(query);

		int res = sqlRequest.executeUpdate(query);
		return 0;
	}


	//Modifier la liste rouge
	public int updateValue(SqlRequest sqlRequest){
		int tmp = areValideDate(sqlRequest);
		if(tmp < 0) return tmp;

		String query = String.format("UPDATE list_rouge SET date_fin = '%s' where id_utilisateur = '%d' and date_debut = '%s'",
			dateSql(end_date), id_user, dateSql(begin_date));

		int res = sqlRequest.executeUpdate(query);
		if(res < 0 ) return -999;
		return res;
	}


	public static int areValideDate(SqlRequest sqlRequest){
		return 0;
	}


	public String toString(){
		return String.format("%d (%s-%s)",id_user,dateSql(begin_date),dateSql(end_date));
	}



}