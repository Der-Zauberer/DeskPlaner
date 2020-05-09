package deskplaner.util;

import java.io.File;

public abstract class Tool implements Command {
	
	private static String name;
	
	public Tool(String name) {
		Tool.name = name;
	}

	@Override
	public abstract boolean onCommand(String label, String[] args, File location);

	@Override
	public abstract String getCommandHelp();
	
	public static String getName() {
		return name;
	}

}
