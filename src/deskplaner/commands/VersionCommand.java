package deskplaner.commands;

import java.io.File;
import deskplaner.handler.CommandHandler;
import deskplaner.main.DeskPlaner;
import deskplaner.util.Command;

public class VersionCommand implements Command {

	@Override
	public boolean onCommand(String label, String args[], File directory) {
		CommandHandler.sendConsoleOutput(DeskPlaner.getName() + " " + DeskPlaner.getVersion());
		CommandHandler.sendConsoleOutput("By " + DeskPlaner.getAuthorsAsString());
		CommandHandler.sendConsoleOutput("Running DeskPlaner on " + System.getProperty("os.name"));
		return true;
	}

	@Override
	public String getCommandHelp() {
		return "Dipslay the current version of the DeskPlaner.";
	}

}
