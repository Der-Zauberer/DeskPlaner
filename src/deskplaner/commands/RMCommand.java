package deskplaner.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import deskplaner.handler.CommandHandler;
import deskplaner.util.Command;

public class RMCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		if(args.length == 0) {
			CommandHandler.sendConsoleOutput(getCommandHelp());
		}
		if(args.length == 1) {
			if(!Files.exists(Paths.get(directory.toString(), args[0]))) {
				CommandHandler.sendConsoleOutput("Could not remove file or directory!");
			}
			try {
				Files.walk(Paths.get(directory.getPath(), args[0])).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
				CommandHandler.sendConsoleOutput("The file or directory " + args[0] + " was removed!");
				return true;
			} catch (IOException exception) {
				CommandHandler.sendConsoleOutput("Could not remove file or directory!");
			}
		}
		return false;
	}

	@Override
	public String getCommandHelp() {
		String string = "rm [name] Remove a file or directory.\n";
		string += "name\tString\tName of the file or directory";
		return string;
	}

}
