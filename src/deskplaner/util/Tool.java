package deskplaner.util;

import java.io.File;

import deskplaner.main.DeskPlaner;

public abstract class Tool implements Command {
	
	private String name;
	
	public Tool(String name) {
		this.name = name;
		if(!getToolDestination().exists()) {
			getToolDestination().mkdirs();
		}
	}

	@Override
	public abstract boolean onCommand(String label, String[] args, File directory);

	@Override
	public abstract String getCommandHelp();
	
	public String getName() {
		return name;
	}
	
	public File getToolDestination() {
		File file = new File(DeskPlaner.getDeskPlanerLocation().toString() + "\\tools\\" + name);
		if(!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

}
