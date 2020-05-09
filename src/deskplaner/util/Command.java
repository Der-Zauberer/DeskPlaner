package deskplaner.util;

import java.io.File;

public interface Command {
	
	public abstract boolean onCommand(String label, String args[], File location);
	public abstract String getCommandHelp();

}
