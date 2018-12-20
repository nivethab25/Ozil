package entities;

public enum Status {
	
toDo("To Do"),inProgress("In Progress"),done("Done");
	final String disp;
	Status(String str){this.disp = str;}
	public String display() {
		return disp;
	}
}

