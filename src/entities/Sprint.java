package entities;

import java.util.ArrayList;
import java.util.Date;

public class Sprint {
	private String SprintTitle;
	private Date SprintBeginDate;
	private Date SprintEndDate;
	
	private ArrayList<Story> StoryList;
	private ArrayList<Story> Todo;
	private ArrayList<Story> inProgress;
	private ArrayList<Story> Done;
	
	//add Sprint section containers
	//ArrayLists?

	public Sprint(String sprintTitle, Date begin, Date end) {
		
		//Init ArrayLists
		StoryList = new ArrayList<Story>();
		Todo = new ArrayList<Story>();
		inProgress = new ArrayList<Story>();
		Done = new ArrayList<Story>();
		
		
		
		
		
		this.SprintTitle = sprintTitle;
		this.SprintBeginDate = begin;
		this.SprintEndDate = end;
	}
	
	
	//Simple Add to container list
	 public void addStoryToList(Story newStory)
	{
		StoryList.add(newStory);
	}
	 
	public void addStoryToTodoList(Story newStory)
	{
		Todo.add(newStory);
	}
	
	public void addStoryToInProgess(Story newStory)
	{
		inProgress.add(newStory);
	}
	
	public void addStoryToDone(Story newStory)
	{
		Done.add(newStory);
	}
	
	//MigrateFrom functions
	
	
	//From list1, to list2
	private int migrateStory(ArrayList<Story> list1,ArrayList<Story> list2, int id)
	{
		if(list1 == null || list2 == null)
		{
			return -1;
		}
		else if (list1.equals(list2))
		{
			//Same list, no migration
			return 1;
		}
		
		
		for(int i = 0;i<list2.size();i++)
		{
			if(list1.get(i).ID == id)
			{
				list2.add(list1.get(i));
				list1.remove(id);
				return 1;
			}
		}
		return -1;
	}
	
	
	/**
	 * Since the containers are private ArrayLists, we need a way to index them. 
	 * This function will take specific strings that will map to one of the containers
	 * and then will migrate a story from list1 to list2
	 * The strings for list1 and list2 are:
	 * "storyList" : StoryList, 
	 * "todo" : Todo, 
	 * "inprogress" : inProgress, 
	 * "done" : Done
	 * 
	 * @param list1 - String 
	 * @param list2 - String
	 * @param id - Integer
	 * @return
	 */
	public int MigrateStory(String list1, String list2,int id)
	{
		
		if (migrateStory(ArrayListMapping(list1),ArrayListMapping(list2),id) != -1)
		{
			return 1;
		}	
		return -1;
	}
	
	
	
	
	
	
	
	
	private ArrayList<Story> ArrayListMapping(String list)
	{
		switch(list)
		{
			case "storyList":
				return StoryList;
			case "todo":
				return Todo;
			case "inprogress":
				return inProgress;
			case "done":
				return Done;
		}
		return null;
	}
	
	
	

	public String getSprintTitle() {
		return SprintTitle;
	}

	public void setSprintTitle(String sprintTitle) {
		SprintTitle = sprintTitle;
	}

	public Date getSprintBeginDate() {
		return SprintBeginDate;
	}

	public void setSprintBeginDate(Date sprintBeginDate) {
		SprintBeginDate = sprintBeginDate;
	}

	public Date getSprintEndDate() {
		return SprintEndDate;
	}
	
	/**
	 * Set the Sprint end Date
	 * @param sprintEndDate
	 */
	public void setSprintEndDate(Date sprintEndDate) {
		SprintEndDate = sprintEndDate;
	}
	
	
	/**
	 * Return String version of date, either end or beginning based on boolean parameter
	 * @param end - boolean: set this to true to get the string of the end date.
	 * @return
	 */
	public String getDateString(boolean end)
	{
		if(end) {
			return SprintEndDate.toString();
		}
		else {
			return SprintBeginDate.toString();
		}
	}
	
	

}
