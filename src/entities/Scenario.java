package entities;

public abstract class Scenario {

	 String title;
	 String description;
	 int ID;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public void editTitle(String newTitle) {
		setTitle(newTitle);
	}
	
	public void editDescription(String newDescription) {
		setDescription(newDescription);
	}

}
