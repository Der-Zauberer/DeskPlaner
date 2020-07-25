package deskplaner.commands;

import java.io.File;

import deskplaner.main.DeskPlaner;
import deskplaner.util.Command;

public class VariableCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		if(args.length == 0) {
			DeskPlaner.sendConsoleOutput(getCommandHelp());
			return false;
		}
		if(args.length == 3 && args[0].equals("add")) {
			if(!DeskPlaner.hasVariable(args[1])) {
				DeskPlaner.addVariable(args[1], args[2]);
				System.out.println("Added " + args[1] + " variable!");
			} else {
				System.out.println("The variable " + args[1] + " already exists!");
			}
		} else if(args.length == 2 && args[0].equals("remove")) {
			if(DeskPlaner.hasVariable(args[1])) {
				DeskPlaner.removeVariable(args[1]);
				System.out.println("Removed " + args[1] + " variable!");
			} else {
				System.out.println("The variable " + args[1] + " does not exist!");
			}
		} else if(args.length == 1 && args[0].equals("list")) {
			for(String string : DeskPlaner.getVariables().keySet()) {
				System.out.println(string +"\t" + DeskPlaner.getVariable(string));
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
