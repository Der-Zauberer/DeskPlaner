package deskplaner.commands;

import java.io.File;
import deskplaner.main.DeskPlaner;
import deskplaner.util.Command;

public class MkDirCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		if(args.length == 0) {
			DeskPlaner.sendConsoleOutput(getCommandHelp());
		}
		if(args.length == 1) {
			boolean success = new File(directory + "\\" + args[0]).mkdir();
			if(!success) {
				DeskPlaner.sendConsoleOutput("Could not create directory!");
			} else {
				DeskPlaner.sendConsoleOutput("The directory " + args[0] + " was created!");
				return true;
			}
		}
		return false;
	}

	@Override
	public String getCommandHelp() {
		String string = "mkdir [name] Create a new directory.\n";
		string += "name\tString\tName of the directory";
		return string;
	}

}
