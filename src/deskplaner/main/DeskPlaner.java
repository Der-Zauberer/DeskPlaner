package deskplaner.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import deskplaner.commands.BrowserCommand;
import deskplaner.commands.CDCommand;
import deskplaner.commands.LSCommand;
import deskplaner.commands.MkDirCommand;
import deskplaner.commands.RMCommand;
import deskplaner.commands.VersionCommand;
import deskplaner.tool.Notes;
import deskplaner.util.ColorsANSI;
import deskplaner.util.Command;
import deskplaner.util.Notification;
import deskplaner.util.Tool;

public class DeskPlaner {
	
	private static final String NAME = "DeskPlaner";
	private static final int VERSION = 0;
	private static final boolean BETA = true;
	private static final String[] AUTHORS = {"Der_Zauberer"};
	
	private static boolean colorproperty = true;
	
	private static HashMap<String, Command> commands = new HashMap<>();
	private static ArrayList<Tool> tools = new ArrayList<>();
	private static File currentdirectory;
	
	public static void main(String[] args) {
		if(System.getProperty("os.name").toLowerCase().contains("windows")) {
			colorproperty = false;
		}
		registerCommand("browser", new BrowserCommand());
		registerCommand("cd", new CDCommand());
		registerCommand("ls", new LSCommand());
		registerCommand("mkdir", new MkDirCommand());
		registerCommand("rm", new RMCommand());
		registerCommand("version", new VersionCommand());
		registerTool(new Notes());
		inititalizeLocation();
		console();
	}
	
	@SuppressWarnings("resource")
	private static void console() {
		new Thread(() -> {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String location = getCurrentDirectory().toString();
				String currentlocation = location.substring(getDeskPlanerLocation().getParentFile().toString().length() + 1);
				currentlocation = currentlocation.replace("\\", "/");
				System.out.print(currentlocation + "~ ");
				String input = scanner.nextLine();
				String command[] = input.split(" ");
				String label = command[0];
				String args[] = Arrays.copyOfRange(command, 1, command.length);
				executeCommand(label, args);
			}
		}).start();
	}
	
	private static void inititalizeLocation() {
		File file = new File(getDeskPlanerLocation().toString() + "\\home");
		if(!file.exists()) {
			file.mkdirs();
		}
		currentdirectory = file;
	}
	
	public static void sendConsoleOutput(Object object) {
		System.out.println(object);
	}
	
	public static void sendConsoleOutput(Object object, int importance) {
		if(colorproperty == true) {
			String color = null;
			switch (importance) {
			case Notification.INFO:	color = ColorsANSI.CYAN; break;
			case Notification.SUCCESS: color = ColorsANSI.GREEN; break;
			case Notification.WARNING: color = ColorsANSI.YELLOW; break;
			case Notification.ERROR: color = ColorsANSI.RED; break;
			default: color = ColorsANSI.RESET; break;
			}
			sendConsoleOutput(color + object.toString() + ColorsANSI.RESET);
		} else {
			sendConsoleOutput(object);
		}
		
	}
	
	public static void registerCommand(String label, Command command) {
		commands.put(label, command);
	}
	
	public static boolean executeCommand(String label, String args[]) {
		if(commands.containsKey(label)) {
			if(args.length > 0 && args[0].equalsIgnoreCase("help")) {
				sendConsoleOutput(commands.get(label).getCommandHelp());
				return true;
			}
			return commands.get(label).onCommand(label, args, getCurrentDirectory());
		}
		return false;
	}
	
	public static void registerTool(Tool tool) {
		tools.add(tool);
		registerCommand(tool.getName().toLowerCase(), tool);
	}
	
	public static ArrayList<Tool> getTools() {
		return tools;
	}
	
	public static HashMap<String, Command> getCommands() {
		return commands;
	}
	
	public static void setCurrentDirectory(File directory) {
		DeskPlaner.currentdirectory = directory;
	}
	
	public static File getCurrentDirectory() {
		return currentdirectory;
	}
	
	public static void openWebsiteInBrowser(String url) {
		try {
			Desktop.getDesktop().browse(new URL(url).toURI());
		} catch (IOException | URISyntaxException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void openFile(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static File getDeskPlanerLocation() {
		File file = null;
		try {
			 file = new File(DeskPlaner.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		} catch (URISyntaxException exception) {}
		if(file.getName().endsWith(".jar")) {
			return file.getParentFile();
		}
		file = new File(file.toString() + "\\DeskPlaner");
		return file;
		
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