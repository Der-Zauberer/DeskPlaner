package deskplaner.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import deskplaner.commands.BrowserCommand;
import deskplaner.commands.VersionCommand;
import deskplaner.util.Command;

public class DeskPlaner {
	
	private static final String NAME = "DeskPlaner";
	private static final int VERSION = 0;
	private static final boolean BETA = true;
	private static final String[] AUTHORS = {"Der_Zauberer"};
	
	private static HashMap<String, Command> commands = new HashMap<>();
	
	public static void main(String[] args) {
		registerCommand("version", new VersionCommand());
		registerCommand("browser", new BrowserCommand());
		console();
	}
	
	@SuppressWarnings("resource")
	private static void console() {
		new Thread(() -> {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.print("DeskPlaner/home~ ");
				String input = scanner.nextLine();
				String command[] = input.split(" ");
				String label = command[0];
				String args[] = Arrays.copyOfRange(command, 1, command.length);
				executeCommand(label, args);
			}
		}).start();
	}
	
	public static void sendConsoleOutput(Object object) {
		System.out.println(object);
	}
	
	public static void registerCommand(String label, Command command) {
		commands.put(label, command);
	}
	
	public static boolean executeCommand(String label, String args[]) {
		if(commands.containsKey(label)) {
			if(args[0].length() > 0 && args[0].equalsIgnoreCase("help")) {
				sendConsoleOutput(commands.get(label).onCommandHelp());
				return true;
			}
			return commands.get(label).onCommand(label, args);
		}
		return false;
	}
	
	public static HashMap<String, Command> getCommands() {
		return commands;
	}
	
	public static void openWebsiteInBrowser(String url) {
		try {
			Desktop.getDesktop().browse(new URL(url).toURI());
		} catch (IOException | URISyntaxException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void openFileInBrowser(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static String getName() {
		return NAME;
	}
	
	public static int getVersion() {
		return VERSION;
	}
	
	public static boolean isBeta() {
		return BETA;
	}
	
	public static String[] getAuthors() {
		return AUTHORS;
	}
	
	public static String getAuthorsAsString() {
		String autors = "";
		for (int i = 0; i < AUTHORS.length; i++) {
			autors = autors + AUTHORS[i];
			if(i > AUTHORS.length -1) {
				autors = autors + ", ";
			}
		}
		return autors;
	}

}