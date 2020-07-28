package deskplaner.commands;

import java.io.File;
import deskplaner.handler.CommandHandler;
import deskplaner.main.DeskPlaner;
import deskplaner.util.Command;

public class VariableCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		if(args.length == 0) {
			CommandHandler.sendConsoleOutput(getCommandHelp());
			return false;
		}
		if(CommandHandler.getCondition(args, "add", 0)) {
			if(!DeskPlaner.hasVariable(args[1])) {
				DeskPlaner.addVariable(args[1], args[2]);
				CommandHandler.sendConsoleOutput("Added " + args[1] + " variable!");
			} else {
				CommandHandler.sendConsoleOutput("The variable " + args[1] + " already exists!");
			}
		} else if(CommandHandler.getCondition(args, "remove", 0)) {
			if(DeskPlaner.hasVariable(args[1])) {
				DeskPlaner.removeVariable(args[1]);
				CommandHandler.sendConsoleOutput("Removed " + args[1] + " variable!");
			} else {
				CommandHandler.sendConsoleOutput("The variable " + args[1] + " does not exist!");
			}
		} else if(CommandHandler.getCondition(args, "list", 0)) {
			for(String string : DeskPlaner.getVariables().keySet()) {
				CommandHandler.sendConsoleOutput(string +"\t" + DeskPlaner.getVariable(string));
			}
		}
		return true;
	}

	@Override
	public String getCommandHelp() {
		String string = "variable <action> [name] [url] Set ore remove a path variable\n";
		string += "action\tString\t\tUse add, remove or list to modify the variables\n";
		string += "name\tString\t\tThe name of the path variable\n";
		string += "url\tString\t\tThe target of the path variable";
		return string;
	}

}
