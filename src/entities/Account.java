package entities;

abstract public class Account {
	protected String FirstName;
	protected String LastName;
	protected int userID; //init when connecting to database
	protected String EmailID; //should be alphanumeric
	protected String Username;
	protected String Password;
	
	
	public Account(String firstName,String lastName,String emailID, String username, String passwd)
	{
		FirstName = firstName;
		LastName = lastName;
		EmailID = emailID;
		Username = username;
		Password = passwd;
	}
	
	public int getUserID(){
		return userID;
	}
	
	
	abstract Account UserType();

	protected abstract void setUserID(int parseInt);
	
	
}
