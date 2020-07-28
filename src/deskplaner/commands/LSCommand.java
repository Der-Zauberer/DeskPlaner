package deskplaner.commands;

import java.io.File;
import deskplaner.handler.CommandHandler;
import deskplaner.util.Command;

public class LSCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		File[] file = directory.listFiles();
		if(file.length == 0) {
			CommandHandler.sendConsoleOutput("No files or folders in directory.");
			return false;
		}
		String directories = "";
		String files = "";
		for (int i = 0; i < file.length; i++) {
			if(file[i].isDirectory()) {
				directories += "/" + file[i].getName() + " ";
			} else {
				files += file[i].getName()  + " ";
			}
		}
		CommandHandler.sendConsoleOutput(directories + files);
		return true;
	}

	@Override
	public String getCommandHelp() {
		String string = "List all files and folders in directory.";
		return string;
	}
	
	

}
