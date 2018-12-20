package entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.io.PrintWriter;
//Author AlexnaderM
/**
 * 
 * @author AlexanderM
 * Starting point for a "project" in the Ozil system when it is finalized. A project contains 
 */
public class Project  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 103686074430709125L;
	private String ProjectTitle;
	private String ProjectID;
	private int AuthorID;
	
	private String ProjectDescription;
	
	private ArrayList<Integer> userIDs; //Project members, only user IDs within the userID list cannot access and modify the project.
	private ArrayList<Story> acceptedStories;
	private ArrayList<Sprint> PlannedSprints;
	
	public Project(String projectTitle, String projectID, int authorID)
	{
		userIDs = new ArrayList<Integer>();
		acceptedStories = new ArrayList<Story>();
		PlannedSprints = new ArrayList<Sprint>();
		
		
		ProjectTitle = projectTitle;
		setProjectID(projectID);
		setAuthorID(authorID);
	}
	
	//public functions
	
	public void reNameProject(String newProjectTitle)
	{
		ProjectTitle = newProjectTitle;
	}
	
	
	public String GetProjectName()
	{
		return ProjectTitle;
	}
	
	public void acceptStory(Story newStory)
	{
		acceptedStories.add(newStory);
	}
	
	
	
	private Story fetchStory(int StoryID)
	{
		
		Iterator<Story> StoryIterator  = acceptedStories.iterator();
		
		Story it;
		
		while(StoryIterator.hasNext())
		{
			it = StoryIterator.next();
			
			if(it.getID() == StoryID)
			{
				return it;
			}
			
		}
		return null;
	}
	
	
	
	
	
	
	

	public String getProjectID() {
		return ProjectID;
	}

	public void setProjectID(String projectID) {
		ProjectID = projectID;
	}

	public int getAuthorID() {
		return AuthorID;
	}

	public void setAuthorID(int authorID) {
		AuthorID = authorID;
	}

	public String getProjectDescription() {
		return ProjectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		ProjectDescription = projectDescription;
	}

	public ArrayList<Integer> getUserIDs() {
		return userIDs;
	}

	public void setUserIDs(ArrayList<Integer> userIDs) {
		this.userIDs = userIDs;
	}
	
	public void addSprint(String SprintTitle,Date Begin, Date End ) {
		PlannedSprints.add(new Sprint(SprintTitle, Begin, End));
		
	}
	
	//add story to a project's sprint
	public void addStoryToSprint(String SprintTitle,int StoryID)
	{
		Iterator<Sprint> SprintList = PlannedSprints.iterator();
		while(SprintList.hasNext())
		{
			
			Sprint currentSprint = SprintList.next();
			if (currentSprint.getSprintTitle().equals(SprintTitle))
			{
				currentSprint.addStoryToList(fetchStory(StoryID));
			}
		}
		
		
	}
	
	
	
	
	
	/**
	 * Placeholder for now, prints out data to report file, then calls the GenerateReport functions in its stories
	 * @param reportOutput
	 */
	public void GenerateReport(PrintWriter reportOutput)
	{
		int i = 0;
		reportOutput.println(ProjectTitle);
		reportOutput.println(ProjectDescription);
		reportOutput.println("Project Author ID: " + AuthorID);
		
		reportOutput.print("Project Members:");
		
		for(i=0;i<userIDs.size()-1;i++)
		{
			reportOutput.print(userIDs.get(i) + ", ");
		}
		reportOutput.println(userIDs.get(i+1) + ". ");
		
		
		
		
	}
	

}
