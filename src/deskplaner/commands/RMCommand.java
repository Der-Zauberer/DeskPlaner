package deskplaner.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import deskplaner.main.DeskPlaner;
import deskplaner.util.Command;

public class RMCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		if(args.length == 1 && Files.exists(Paths.get(directory.toString(), args[0]))) {
			try {
				Files.walk(Paths.get(directory.getPath(), args[0])).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
				DeskPlaner.sendConsoleOutput("The file or directory " + args[0] + " was removed!");
			} catch (IOException exception) {
				DeskPlaner.sendConsoleOutput("Could not remove file or directory!");
			}
		}
		return true;
	}

	@Override
	public String getCommandHelp() {
		String string = "rm [name] Remove a files or directory.\n";
		string += "name\tString\tName of the file or directory";
		return string;
	}

}
