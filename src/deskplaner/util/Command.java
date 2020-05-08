package deskplaner.util;

public interface Command {
	
	public abstract boolean onCommand(String label, String args[]);

}
