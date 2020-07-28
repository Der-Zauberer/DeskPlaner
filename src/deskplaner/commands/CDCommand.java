package deskplaner.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import deskplaner.handler.CommandHandler;
import deskplaner.handler.FileHandler;
import deskplaner.util.Command;

public class CDCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		if(args.length == 0) {
			CommandHandler.sendConsoleOutput(getCommandHelp());
		} else if(args[0].equalsIgnoreCase(".")){
			CommandHandler.sendConsoleOutput("Directory does not exist!");
		} else {
			if(args[0].equalsIgnoreCase("..")) {
				if(FileHandler.getDeskPlanerDirectory().equals(directory)) {
					CommandHandler.sendConsoleOutput("Directory does not exist!");
					return true;
				}
				FileHandler.setCurrentDirectory(FileHandler.getCurrentDirectory().getParentFile());
				return true;
			} else if(args[0].startsWith("/") && new File(directory.toString() + args[0].replace("/", "\\")).exists()) {
				FileHandler.setCurrentDirectory(new File(directory.toString() + args[0].replace("/", "\\")));
				return true;
			} else if(Files.exists(Paths.get(directory.toString(), args[0]))) {
				FileHandler.setCurrentDirectory(new File(directory.toString(), args[0]));
				return true;
			} else {
				CommandHandler.sendConsoleOutput("Directory does not exist!");
			}
		}
		return false;
	}

	@Override
	public String getCommandHelp() {
		String string = "cd [directory] Change directory.\n";
		string += "directory\tString\tThe name of the folder\n";
		string += "directory\tString\tThe path of the directory\n";
		string += "directory\tString\t.. to go backwards";
		return string;
	}

}
