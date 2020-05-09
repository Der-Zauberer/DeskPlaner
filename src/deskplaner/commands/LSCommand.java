package deskplaner.commands;

import java.io.File;
import deskplaner.main.DeskPlaner;
import deskplaner.util.Command;

public class LSCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File location) {
		File[] file = location.listFiles();
		String directories = "";
		String files = "";
		for (int i = 0; i < file.length; i++) {
			if(file[i].isDirectory()) {
				directories += "/" + file[i].getName() + " ";
			} else {
				files += file[i].getName()  + " ";
			}
		}
		DeskPlaner.sendConsoleOutput(directories + files);
		return true;
	}

	@Override
	public String getCommandHelp() {
		String string = "List all files and folders in directory.";
		return string;
	}
	
	

}