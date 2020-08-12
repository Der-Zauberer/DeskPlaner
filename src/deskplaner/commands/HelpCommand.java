package deskplaner.commands;

import java.io.File;

import deskplaner.handler.CommandHandler;
import deskplaner.util.Command;

public class HelpCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		CommandHandler.sendConsoleOutput(getCommandHelp());
		return true;
	}

	@Override
	public String getCommandHelp() {
		String string = "You can use the following commands:";
		for (String command : CommandHandler.getCommands().keySet()) {
			string += "\n" + command.toUpperCase();
		}
		string += "\nIf you need help with command type <command> help";
		return string;
	}

}
