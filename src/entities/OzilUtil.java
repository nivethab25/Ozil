package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javafx.util.Pair;

public class OzilUtil {
	private static User currentUser;
	static ArrayList<String> Ts = new ArrayList<String>();
	private static final int LOW = 3, HIGH = 8, MEDIUM = 5, CRITICAL = 10;

	private static SQLManager dbInterface = null;

	private static void setdb() {
		if (dbInterface == null) {
			Ts.add("CREATE TABLE users(userId integer,fName varchar(60),lName varchar(60),email varchar(60),userName varchar(60),password varchar(64),primary key(userId));");
			// Ts.add("CREATE TABLE projects(projectId INTEGER, projectTitle
			// VARCHAR(60),data BLOB, PRIMARY KEY(projectId); ");
			// Ts.add("CREATE TABLE user_projects("
			// + "index INTEGER,"
			// + "userId INTEGER NOT NULL,"
			// + "projectId INTEGER NOT NULL"
			// + "PRIMARY KEY(index),"
			// + "FOREIGN KEY(userId) REFERENCES users(userId),"
			// + "FOREIGN KEY(projectId) REFERENCES projects(projectID));");
			//
			Ts.add("CREATE TABLE story(storyId integer, title varchar(60),description varchar(100),primary key(storyId));");
			Ts.add("CREATE TABLE task(taskId integer, storyId integer, userId integer, title varchar(60),description varchar(100),priority varchar(20),status varchar(20),primary key(taskId), FOREIGN KEY(storyId) REFERENCES story(storyId), FOREIGN KEY(userId) REFERENCES users(userId));");
			Ts.add("CREATE TABLE defect(defectId integer, storyId integer, userId integer, title varchar(60),description varchar(100),priority varchar(20),status varchar(20),primary key(defectId), FOREIGN KEY(storyId) REFERENCES story(storyId), FOREIGN KEY(userId) REFERENCES users(userId));");
			SQLManager.getSQLManager("OzilUsersDB.db", "SELECT name FROM sqlite_master WHERE type='table'", Ts, "");
		}
	}

	/*
	 * Initialize an empty data base
	 */

	/**
	 * Addes a new user to the database
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static int addUser(String firstName, String lastName, String email, String username, String passwrd)
			throws ClassNotFoundException, SQLException {
		if (SQLManager.getCon() == null) {
			return -1;
		}

		PreparedStatement prep = SQLManager.preparedStatement("INSERT INTO users values(?, ?, ?, ?, ?, ?);");
		prep.setString(2, firstName);
		prep.setString(3, lastName);
		prep.setString(4, email);
		prep.setString(5, username);
		prep.setString(6, passwrd);
		prep.execute();

		Connection conn = SQLManager.getCon();
		Statement stmt = conn.createStatement();
		ResultSet id = stmt.executeQuery("SELECT userId FROM users WHERE fName ='" + firstName + "' AND lName = '"
				+ lastName + "' AND email='" + email + "' AND userName='" + username + "';");
		// sql query to return user ID

		if (id.getRow() == 0) {
			int ret = id.getInt(1);

			// conn.close();
			return ret;
		}

		else {
			// conn.close();
			return -1;
		}

	}

	/***
	 * Check for valid email format
	 */
	public static boolean validateEmail(String emailstr) {
		// email regex
		Pattern pat = Pattern.compile(
				"^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$");

		if (emailstr == null) {
			return false;
		}

		return pat.matcher(emailstr).matches();
	}

	/**
	 * 
	 * 
	 * Returns: a user object for the new user, or null for exit
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean signIn(String username, String paswd) throws ClassNotFoundException, SQLException {

		// get username and password
		// if corresponding username and password exist in database, create a user
		// object for that

		// sqlmanager.executeQuery();

		Connection conn = SQLManager.getCon();
		Statement stmt = conn.createStatement();
		ResultSet set = stmt.executeQuery(
				"SELECT password,fName,lName,email,userId FROM users WHERE username= '" + username + "'; ");
		if (set.next()) {
			if (set.getString(1).isEmpty() == true) {
				// conn.close();
				return false;
			}

			else if (set.getString(1).equals(paswd)) {
				currentUser = new User(set.getString(2), set.getString(3), set.getString(4), username, paswd);
				currentUser.setUserID(Integer.parseInt(set.getString(5)));
				// conn.close();
				return true;
			}

		}

		return false;
	}

	public static boolean signUp(String fName, String lName, String email, String username, String paswd)
			throws ClassNotFoundException, SQLException {

		// email validation

		if (!validateEmail(email)) {
			return false;
		}

		int userid = addUser(fName, lName, email, username, paswd); // add the user to the database
		currentUser = new User(fName, lName, email, username, paswd);
		currentUser.setUserID(userid);

		return true;

	}

	public static boolean createTask(Task task) throws ClassNotFoundException, SQLException {

		if (SQLManager.getCon() == null) {
			return false;
		}

		// get username and password
		// if corresponding username and password exist in database, create a user
		// object for that

		// sqlmanager.executeQuery();
		int taskStory = task.getStoryID();
		String taskTitle = task.getTitle();
		String taskDesc = task.getDescription();
		String taskPriority = task.getPriority();
		String taskStatus = task.getStatus().display();

		PreparedStatement prep = SQLManager.preparedStatement("INSERT INTO task values(?, ?, ?, ?, ?, ?, ?);");
		prep.setInt(2, taskStory);
		prep.setString(4, taskTitle);
		prep.setString(5, taskDesc);
		prep.setString(6, taskPriority);
		prep.setString(7, taskStatus);
		boolean res = prep.execute();

		return !res;

	}

	public static boolean logDefect(Defect defect) throws ClassNotFoundException, SQLException {

		if (SQLManager.getCon() == null) {
			return false;
		}

		// get username and password
		// if corresponding username and password exist in database, create a user
		// object for that

		// sqlmanager.executeQuery();
		int defectStory = defect.getStoryID();
		String defectTitle = defect.getTitle();
		String defectDesc = defect.getDescription();
		String defectPriority = defect.getPriority();
		String defectStatus = defect.getStatus().display();

		PreparedStatement prep = SQLManager.preparedStatement("INSERT INTO defect values(?, ?, ?, ?, ?, ?, ?);");
		prep.setInt(2, defectStory);
		prep.setString(4, defectTitle);
		prep.setString(5, defectDesc);
		prep.setString(6, defectPriority);
		prep.setString(7, defectStatus);
		boolean res = prep.execute();

		return !res;

	}

	
	public static boolean createStory(Story story) throws ClassNotFoundException, SQLException {
		if (SQLManager.getCon() == null) {
			return false;
		}

		PreparedStatement prep = SQLManager.preparedStatement("INSERT INTO story values(?, ?, ?);");
		prep.setString(2, story.getTitle());
		prep.setString(3, story.getDescription());
		boolean res = prep.execute();
		return !res;

	}

	public static boolean updateStatus(int taskId, String status) throws ClassNotFoundException, SQLException {
		if (SQLManager.getCon() == null) {
			return false;
		}

		PreparedStatement prep = SQLManager.preparedStatement("UPDATE task SET status = ? where taskId = ? ;");
		prep.setString(1, status);
		prep.setInt(2, taskId);
		boolean res = prep.execute();
		return !res;

	}

	public static boolean assignTo(int taskId, int userId) throws ClassNotFoundException, SQLException {
		if (SQLManager.getCon() == null) {
			return false;
		}

		PreparedStatement prep = SQLManager.preparedStatement("UPDATE task SET userId = ? where taskId = ? ;");
		prep.setInt(1, userId);
		prep.setInt(2, taskId);
		boolean res = prep.execute();
		return !res;

	}
	
	public static boolean updateDefectStatus(int taskId, String status) throws ClassNotFoundException, SQLException {
		if (SQLManager.getCon() == null) {
			return false;
		}

		PreparedStatement prep = SQLManager.preparedStatement("UPDATE defect SET status = ? where defectId = ? ;");
		prep.setString(1, status);
		prep.setInt(2, taskId);
		boolean res = prep.execute();
		return !res;

	}

	public static boolean assignDefectTo(int taskId, int userId) throws ClassNotFoundException, SQLException {
		if (SQLManager.getCon() == null) {
			return false;
		}

		PreparedStatement prep = SQLManager.preparedStatement("UPDATE defect SET userId = ? where defectId = ? ;");
		prep.setInt(1, userId);
		prep.setInt(2, taskId);
		boolean res = prep.execute();
		return !res;

	}
	
	public static Pair<Integer,Pair<Integer,Integer>> computeEffortsNprogress(int storyId) throws ClassNotFoundException, SQLException{
		int doneCt = 0, total = 0, effortsLogged = 0, effortsRemaining = 0;
		HashMap<Integer,Pair<String,String>> tasks = getTasks(storyId);
		for(Entry<Integer,Pair<String,String>> entry:tasks.entrySet()) {
			Pair<String,String> pair = entry.getValue();
			if(pair.getKey().equalsIgnoreCase("done")) {
				doneCt++;
				if(pair.getValue().equalsIgnoreCase("low"))
					effortsLogged += LOW;
				else if(pair.getValue().equalsIgnoreCase("high"))
					effortsLogged += HIGH;
				else if(pair.getValue().equalsIgnoreCase("medium"))
					effortsLogged += MEDIUM;
				else
					effortsLogged += CRITICAL;
			}
			else {
				if(pair.getValue().equalsIgnoreCase("low"))
					effortsRemaining += LOW;
				else if(pair.getValue().equalsIgnoreCase("high"))
					effortsRemaining += HIGH;
				else if(pair.getValue().equalsIgnoreCase("medium"))
					effortsRemaining += MEDIUM;
				else
					effortsRemaining += CRITICAL;
			}	
			
		}
		
		HashMap<Integer,Pair<String,String>> defects = getDefects(storyId);
		for(Entry<Integer,Pair<String,String>> entry:defects.entrySet()) {
			Pair<String,String> pair = entry.getValue();
			if(pair.getKey().equalsIgnoreCase("done")){
				doneCt++;
				if(pair.getValue().equalsIgnoreCase("low"))
					effortsLogged += LOW;
				else if(pair.getValue().equalsIgnoreCase("high"))
					effortsLogged += HIGH;
				else if(pair.getValue().equalsIgnoreCase("medium"))
					effortsLogged += MEDIUM;
				else
					effortsLogged += CRITICAL;
			}
			else {
				if(pair.getValue().equalsIgnoreCase("low"))
					effortsRemaining += LOW;
				if(pair.getValue().equalsIgnoreCase("high"))
					effortsRemaining += HIGH;
				if(pair.getValue().equalsIgnoreCase("medium"))
					effortsRemaining += MEDIUM;
				else
					effortsRemaining += CRITICAL;
			}	
		}
		total = tasks.size()+defects.size();
		Double progress = 0.0;
		if(total != 0)
			progress = Math.ceil((new Double(doneCt)/new Double(total))*100);
		System.out.println(progress);
		return new Pair<Integer,Pair<Integer,Integer>>(progress.intValue(),new Pair<Integer,Integer>(effortsLogged,effortsRemaining));
	}
	
	public static HashMap<Integer,Pair<String,String>> getTasks(int storyId) throws ClassNotFoundException, SQLException {
		HashMap<Integer,Pair<String,String>> tasks = new HashMap<Integer,Pair<String,String>>();
		if (SQLManager.getCon() == null) {
			return null;
		}

		Connection conn = SQLManager.getCon();
		Statement stmt = conn.createStatement();
		ResultSet set = stmt.executeQuery("SELECT taskId, status, priority FROM task where storyId = '" + storyId + "'; ");
		while (set.next()) {
			if (set.getInt(1) <= 0) {
				// conn.close();
				return null;
			}

			else {
				tasks.put( set.getInt(1),new Pair<String, String>(set.getString(2),set.getString(3)));
			}
		}
		return tasks;

	}
	
	public static HashMap<Integer,Pair<String,String>> getDefects(int storyId) throws ClassNotFoundException, SQLException {
		HashMap<Integer,Pair<String,String>> defects = new HashMap<Integer,Pair<String,String>>();
		if (SQLManager.getCon() == null) {
			return null;
		}

		Connection conn = SQLManager.getCon();
		Statement stmt = conn.createStatement();
		ResultSet set = stmt.executeQuery("SELECT defectId, status, priority FROM defect where storyId = '" + storyId + "'; ");
		while (set.next()) {
			if (set.getInt(1) <= 0) {
				// conn.close();
				return null;
			}

			else {
				defects.put( set.getInt(1),new Pair<String, String>(set.getString(2),set.getString(3)));
			}
		}
		return defects;

	}
	
	public static HashMap<Integer,String> getUsers() throws ClassNotFoundException, SQLException {
		HashMap<Integer,String> names = new HashMap<Integer,String>();
		if (SQLManager.getCon() == null) {
			return null;
		}

		Connection conn = SQLManager.getCon();
		Statement stmt = conn.createStatement();
		ResultSet set = stmt.executeQuery("SELECT userName, userId FROM users; ");
		while (set.next()) {
			if (set.getInt(2) <= 0) {
				// conn.close();
				return null;
			}

			else {
				names.put( set.getInt(2),set.getString(1));
			}
		}
		return names;

	}
	
	public static int getUserId(Object user) throws ClassNotFoundException, SQLException {
		
		if (SQLManager.getCon() == null) {
			return -1;
		}

		String username = (String)user;
		Connection conn = SQLManager.getCon();
		Statement stmt = conn.createStatement();
		ResultSet set = stmt.executeQuery("SELECT userId FROM users where userName = '"+username+"' ; ");
		if(set.getRow()==0)
			return set.getInt(1);
		else
			return -1;
		
	}

    public static String getUser(int user) throws ClassNotFoundException, SQLException {
		
		if (SQLManager.getCon() == null) {
			return null;
		}

		Connection conn = SQLManager.getCon();
		Statement stmt = conn.createStatement();
		ResultSet set = stmt.executeQuery("SELECT userName FROM users where userId = '"+user+"' ; ");
		if(set.getRow()==0)
			return set.getString(1);
		else
			return null;
		
	}
	
	public static boolean createProject(Project project) throws ClassNotFoundException, SQLException {
		if (SQLManager.getCon() == null) {
			return false;
		}

		// PreparedStatement preparedStatement = SQLManager.preparedStatement("INSERT
		// INTO project(?,?,?")

		return false;
	}
	
	
	public static boolean createSprint(Sprint sprint) throws ClassNotFoundException, SQLException
	{
		if(SQLManager.getCon() == null) {
			return false;
		}
		
		//PreparedStatement preparedStatement = SQLManager.preparedStatement("INSERT INTO sprint(");
		return true;
	}

	public static void initProject() throws ClassNotFoundException, SQLException {
		// currentUser = null;

		//
		setdb();
		// connect to database
		if (SQLManager.getCon() == null) {

			SQLManager.getCon();

		}

	}

	public static Account getCurrentUser() {
		return currentUser;

	}

}
