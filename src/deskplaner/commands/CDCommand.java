package deskplaner.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import deskplaner.main.DeskPlaner;
import deskplaner.util.Command;

public class CDCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		if(args.length == 0) {
			DeskPlaner.sendConsoleOutput("Arguments required!");
			DeskPlaner.sendConsoleOutput(getCommandHelp());
		} else if(args[0].equalsIgnoreCase(".")){
			DeskPlaner.sendConsoleOutput("Directory does not exist!");
		} else {
			if(args[0].equalsIgnoreCase("..")) {
				if(DeskPlaner.getDeskPlanerLocation().equals(directory)) {
					DeskPlaner.sendConsoleOutput("Directory does not exist!");
					return true;
				}
				DeskPlaner.setCurrentLocation(DeskPlaner.getCurrentLocation().getParentFile());
			} else if(args[0].startsWith("/") && new File(directory.toString() + args[0].replace("/", "\\")).exists()) {
				DeskPlaner.setCurrentLocation(new File(directory.toString() + args[0].replace("/", "\\")));
			} else if(Files.exists(Paths.get(directory.toString(), args[0]))) {
				DeskPlaner.setCurrentLocation(new File(directory.toString(), args[0]));
			} else {
				DeskPlaner.sendConsoleOutput("Directory does not exist!");
			}
		}
		return true;
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
