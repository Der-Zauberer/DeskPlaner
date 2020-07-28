package deskplaner.handler;

import java.util.Arrays;
import java.util.HashMap;
import deskplaner.util.Command;

public class CommandHandler {
	
	private static HashMap<String, Command> commands = new HashMap<>();
	
	public static void registerCommand(String label, Command command) {
		commands.put(label, command);
	}
	
	public static boolean executeCommand(String string) {
		String command[] = string.split(" ");
		String label = command[0];
		String args[] = Arrays.copyOfRange(command, 1, command.length);
		return executeCommand(label, args);
	}
	
	public static boolean executeCommand(String label, String args[]) {
		if(commands.containsKey(label)) {
			if(args.length > 0 && args[0].equalsIgnoreCase("help")) {
				sendConsoleOutput(commands.get(label).getCommandHelp());
				return true;
			}
			return commands.get(label).onCommand(label, args, FileHandler.getCurrentDirectory());
		}
		return false;
	}
	
	public static HashMap<String, Command> getCommands() {
		return commands;
	}
	
	public static boolean getCondition(String args[], String condition, int position) {
		if(args.length - 1 >= position && args[position] != null && args[position].equalsIgnoreCase(condition)) return true;
		return false;
	}
	
	public static boolean hasCondition(String args[], String condition) {
		for (int i = 0; i < args.length; i++) {
			if(args[i].equalsIgnoreCase(condition)) return true;
		}
		return false;
	}
	
	public static void sendConsoleOutput(Object object) {
		System.out.println(object);
	}

}
