package entities;

/**
 * 
 * @author AlexanderM
 *This will be a more fleshed out Administrator class, specifically give to project administrators. It will have more use organizational and administrative tools.
 *It should also be able to manipulate other projects that it is not necessarily attached to. 
 *
 *TDD - TO BE DEVELOPED
 */




public class Admin extends Account{

	public Admin(String FirstName,String LastName,String EmailID,String Username,String Password) {
		super(FirstName, LastName, EmailID, Username, Password);
		// TODO Auto-generated constructor stub
	}

	@Override
	Account UserType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setUserID(int parseInt) {
		userID = parseInt;
		
	}
	
	
	

}
