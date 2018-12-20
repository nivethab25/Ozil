package entities;

import java.util.ArrayList;

//@ Author: Nivetha Babu

public class Story extends Scenario {

	private ArrayList<Task> pendingTasks; 

	private ArrayList<Task> AcceptedTasks;


	public Story(String title, String description) {
		this.title = title;
		this.description = description;
		pendingTasks = new ArrayList<Task>();
		AcceptedTasks = new ArrayList<Task>();
	}

	public void addNewTask(String taskTitle, String taskDescription, String priority,Status status)
	{
		//task id is generated based on number of currently existing tasks :
		//-Problem: this means we might have multiple tasks with the same taskID, SOLVE. implement MAX task capacity?, add old task ids to a list or the db?
		pendingTasks.add(new Task(taskTitle, taskDescription, priority, status, ID));
	}

	public void approveTask(String taskTitle, int taskID)
	{
		for(int i=0;i<pendingTasks.size();i++)
		{
			if(pendingTasks.get(i).getID() == taskID)
			{
				AcceptedTasks.add(pendingTasks.get(i));
				pendingTasks.remove(i);
			}
		}
	}


	public void approveTask(String taskName, int taskID, int pos)
	{
		AcceptedTasks.add(pendingTasks.get(pos));
		pendingTasks.remove(pos);
	}


	/*
	 * remove task from the project entirely, regardless if on pending or accepted list
	 * 
	 *  
	 */
	public void removeTask(String taskName, int taskID)
	{
		int i = 0;
		ArrayList<Task> listptr = null;
		int min = Math.min(AcceptedTasks.size(),pendingTasks.size());
		int max = Math.max(AcceptedTasks.size(),pendingTasks.size());
		

		
		//Search through the first min of the list
		for(i = 0; i < min;i++)
		{
			if(AcceptedTasks.get(i).getID() == taskID)
			{
				AcceptedTasks.remove(i);
			}
			else if(pendingTasks.get(i).getID() == taskID)
			{
				pendingTasks.remove(i);
			}
		}
		if(max == AcceptedTasks.size())
		{
			listptr = AcceptedTasks;
		}
		else
		{
			listptr = pendingTasks;
		}
		for(int z  = i; z < listptr.size(); z++ )
		{
			if(listptr.get(z).getID() == taskID)
			{
				listptr.remove(z);
			}
		}
		
		
		
	}

}
