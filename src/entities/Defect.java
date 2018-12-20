package entities;

import java.util.ArrayList;

//@ Author: Nivetha Babu

public class Defect extends Scenario{
	private ArrayList<TestCase> AcceptanceTests;
	
	private String priority;
	private int storyID;
	private Status status;
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public Defect(String defectName, String descrptn, String priorty, Status Status, int storyId){
		title = defectName;
		description = descrptn; 
		priority = priorty;
		storyID = storyId;
		status = Status;
		AcceptanceTests = new ArrayList<TestCase>();
		
	}
	
	public int getStoryID() {
		return storyID;
	}

	public void setStoryID(int storyID) {
		this.storyID = storyID;
	}

	public void editPriority(String newPriority) {
		setPriority(newPriority);
	}
	
	public void addTestCase(String Name, String Desc) {
		AcceptanceTests.add(  new TestCase(Name,Desc,AcceptanceTests.size()+1)   );
	}
	
	public int DeleteTestCaseByName(String Name)
	{
		for(int i = 0; i < AcceptanceTests.size(); i++)
		{
			if(AcceptanceTests.get(i).getTestCaseName().equals(Name)) {
				AcceptanceTests.remove(i);
				return 1;
			}
		}
		return 0;
	}
	
	public int DeleteTestCaseByID(int id)
	{
		for(int i = 0; i < AcceptanceTests.size(); i++)
		{
			if(AcceptanceTests.get(i).getTestCaseID() == id) {
				AcceptanceTests.remove(i);
				return 1;
			}
		}
		return 0;
	}

}

