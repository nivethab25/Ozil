
package entities;

import java.sql.*;
import java.util.ArrayList;




public class SQLManager
{
	private static Connection con;
	private static boolean existingDB = false;
	private static String DataBaseTarget;
	private static String PathToDataBase;
	@SuppressWarnings("unused") //We set tables in constructor
	private static ArrayList<String> tables;
	private static SQLManager sqlInstance = null;
	
	//Path where we want to target database
	/**
	 * 
	 * @param DataBaseName - Name of Database file
	 * @param initQuery : String - Initial Query to generate database, if not generated before.
	 * @param Tables : String ArrayList - Used to Run a set of sql queries on start up
	 */
	private SQLManager(String DataBaseName, String initQuery, ArrayList<String> Tables){
		if(sqlInstance == null) {
			DataBaseTarget = DataBaseName; //Set the name of our database
			tables = Tables;
			PathToDataBase = null;
			
			try{
				getConnection(); //get a connection to our db, sets con value
				existingDB = checkForDB();
				initializeDB(initQuery);	
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			catch(Exception c)
			{
				c.printStackTrace();
			}
		
		}
		
	}
	
	private SQLManager(String DataBaseName, String initQuery, ArrayList<String> Tables, String PATH){
		if(sqlInstance == null) {
			DataBaseTarget = DataBaseName; //Set the name of our database
			tables = Tables;
			PathToDataBase = PATH;
			
			try{
				getConnection(); //get a connection to our db, sets con value
				existingDB = checkForDB();
				initializeDB(initQuery);	
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			catch(Exception c)
			{
				c.printStackTrace();
			}
		
		}
		
	}
	
	
	
	/**
	 * 
	 * @param DataBaseName
	 * @param initQuery
	 * @param Tables
	 * @return
	 */
	public static SQLManager getSQLManager(String DataBaseName, String initQuery, ArrayList<String> Tables) {
		if(sqlInstance == null)
		sqlInstance = new SQLManager(DataBaseName, initQuery, Tables);
		return sqlInstance;
	}
	
	
	
	 
	 
	 
	 
	 
	 /**
	  * 
	  * Initialize and retrieve the database connection with a specified database path.
	  * @param DataBaseName
	  * @param initQuery
	  * @param Tables
	  * @param PATH
	  * @return
	  */
	public static SQLManager getSQLManager(String DataBaseName, String initQuery, ArrayList<String> Tables, String PATH) {
		if(sqlInstance == null)
		sqlInstance = new SQLManager(DataBaseName, initQuery, Tables, PATH);
		return sqlInstance;
	}
	
	
	/**
	 * Check if database exists
	 * <boolean>
	 * @return existingDB : boolean
	 */
	public boolean dbExists()
	{
		return existingDB;
	}
	
	
	
	
	/*
	 * "SELECT fname, lname FROM users"
	 * 
	 * change from ResultSet to boolean
	 */
	public static boolean checkForDB() throws SQLException  
	{
		
		if(con != null)
		{
			
			ResultSet resultSet = con.getMetaData().getCatalogs();

			//iterate each catalog in the ResultSet
			while (resultSet.next()) 
			{
  				// Get the database name, which is at position 1
  				String dbName = resultSet.getString(1);
				if(dbName.equals(DataBaseTarget.substring(0,DataBaseTarget.indexOf(".")-1)))
				{
					return true;
				}
				
		}	
		}
		return false;
	}
	
		
	public static void initializeDB(String initQuery) throws SQLException {
		if(!existingDB == true)
		{					
				System.out.println("building new database now...");
				Statement state1 = con.createStatement();
				String q1 = "CREATE TABLE IF NOT EXISTS users(userId integer,fName varchar(60),lName varchar(60),email varchar(60),userName varchar(60),password varchar(64),primary key(userId));";								
				String q2 = "CREATE TABLE IF NOT EXISTS story(storyId integer, title varchar(60),description varchar(100),primary key(storyId));";
				String q3 = "CREATE TABLE IF NOT EXISTS task(taskId integer, storyId integer, userId integer, title varchar(60),description varchar(100),priority varchar(20),status varchar(20),primary key(taskId), FOREIGN KEY(storyId) REFERENCES story(storyId), FOREIGN KEY(userId) REFERENCES users(userId));";
				String q4 = "CREATE TABLE IF NOT EXISTS defect(defectId integer, storyId integer, userId integer, title varchar(60),description varchar(100),priority varchar(20),status varchar(20),primary key(defectId), FOREIGN KEY(storyId) REFERENCES story(storyId), FOREIGN KEY(userId) REFERENCES users(userId));";
				state1.executeUpdate(q1);
				state1.executeUpdate(q2);
				state1.executeUpdate(q3);
				state1.executeUpdate(q4);
				existingDB = true;
			}
			else
			{
				System.out.println("These tables seem to exist. Canceling new Database creation");
			}
		
		
	}
	
	
	
	
	
	
	private static void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		
		if(PathToDataBase == null) {
		con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseTarget);
		}
		else {
			con = DriverManager.getConnection("jdbc:sqlite:" + PathToDataBase + DataBaseTarget);
		}
		//Add another line for project Database
		//initializeDB(DataBaseTarget);
		
	}
	
	
	public static Connection getCon() throws ClassNotFoundException, SQLException {
		if(con == null)
		{
			getConnection();
		}
		
		return con;
	}

	public static void setCon(Connection conn) {
		con = conn;
	}


	public static PreparedStatement preparedStatement(String string) throws SQLException {
		// TODO Auto-generated method stub		
		return con.prepareStatement(string);
	}
	
	
	
	
}
