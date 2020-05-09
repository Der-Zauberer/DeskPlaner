package deskplaner.commands;

import deskplaner.main.DeskPlaner;
import deskplaner.util.Command;

public class VersionCommand implements Command {

	@Override
	public boolean onCommand(String label, String args[]) {
		DeskPlaner.sendConsoleOutput(DeskPlaner.getName() + " v" + DeskPlaner.getVersion());
		DeskPlaner.sendConsoleOutput("By " + DeskPlaner.getAuthorsAsString());
		return true;
	}

	@Override
	public String onCommandHelp() {
		return "Dipslay the current version of the DeskPlaner.";
	}

}
