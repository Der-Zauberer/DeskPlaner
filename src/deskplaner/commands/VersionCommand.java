package deskplaner.commands;

import java.io.File;
import deskplaner.handler.CommandHandler;
import deskplaner.main.DeskPlaner;
import deskplaner.util.Command;

public class VersionCommand implements Command {

	@Override
	public boolean onCommand(String label, String args[], File directory) {
		String string = DeskPlaner.getName() + " " + DeskPlaner.getVersion() + "\n";
		string += "By " + DeskPlaner.getAuthorsAsString() + "\n";
		string += "Running DeskPlaner on " + System.getProperty("os.name");
		CommandHandler.sendConsoleOutputBlock(string);
		return true;
	}

	@Override
	public String getCommandHelp() {
		return "Display the current version of the DeskPlaner.";
	}

}
