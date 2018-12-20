package entities;

/**
 * 
 * @author Alexander Moreno
 * 
 * Class to describe Acceptance Tests or Test Cases for a task
 * 
 *
 */
public class TestCase {
	/**
	 *  String : Name of TestCase
	 */
	private String testCaseName; //TestCase name
	private String testCaseDesc; //Test Case Description
	private String status;
	private int testCaseID;
	
	
	
	/**
	 * Initialize a test Case for a Task class instance
	 * 
	 * @param testCaseName : String - Title of the TestCase, also used to find a specific TestCase in  a Task
	 * @param testCaseDesc : String - Test Case Description
	 * @param id : Integer - Id of TestCase given by holding Task
	 * 
	 * @param <optional> initalStatus : String
	 */
	public TestCase(String testCaseName, String testCaseDesc,int id) {
		setTestCaseName(testCaseName);
		setTestCaseDesc(testCaseDesc);
		setStatus("Incomplete"); //by default, a TestCase associated is associated with a task that is incomplete
		setTestCaseID(id);
		
	}
	
	//Replace Status with actual status class?, I think so.
	/**
	 * 
	 * @param testCaseName
	 * @param testCaseDesc
	 * @param id
	 * @param initialStatus
	 */
	public TestCase(String testCaseName, String testCaseDesc, int id, String initialStatus)
	{
		setTestCaseName(testCaseName);
		setTestCaseDesc(testCaseDesc);
		setStatus(initialStatus);
		setTestCaseID(id);
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseTitle) {
		this.testCaseName = testCaseTitle;
	}

	public String getTestCaseDesc() {
		return testCaseDesc;
	}

	public void setTestCaseDesc(String testCaseDesc) {
		this.testCaseDesc = testCaseDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTestCaseID() {
		return testCaseID;
	}

	public void setTestCaseID(int testCaseID) {
		this.testCaseID = testCaseID;
	}
	
	public void createNewTestCase(String TestCaseName)
	{
		
	}
	
	public TestCase openTestCase(String TestCaseName)
	{
		return this;
		
	}
	
	
	
	
	

}
