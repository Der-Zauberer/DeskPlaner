package deskplaner.main;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import deskplaner.commands.BrowserCommand;
import deskplaner.commands.CDCommand;
import deskplaner.commands.LSCommand;
import deskplaner.commands.MkDirCommand;
import deskplaner.commands.RMCommand;
import deskplaner.commands.VersionCommand;
import deskplaner.scenes.Editor;
import deskplaner.util.ColorsANSI;
import deskplaner.util.Command;
import deskplaner.util.Notification;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeskPlaner extends Application {
	
	private static final String NAME = "DeskPlaner";
	private static final int VERSION = 0;
	private static final boolean BETA = true;
	private static final String[] AUTHORS = {"Der_Zauberer"};
	
	private static boolean colorproperty = true;
	private static boolean guiproperty = true;
	
	private static Stage stage;
	private static File currentdirectory;
	private static HashMap<String, Command> commands = new HashMap<>();
	
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
		currentdirectory = inititalizeDirectory("home");
		inititalizeDirectory("tools");
		console();
		if(guiproperty)	launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage = new Stage();
		DeskPlaner.stage = stage;
		stage.setScene(new Editor());
		stage.setTitle(getName());
		stage.setHeight(720);
		stage.setWidth(1020);
		stage.show();
	}
	
	@SuppressWarnings("resource")
	private static void console() {
		new Thread(() -> {
			String prefix = "";
			String suffix = "";
			if(colorproperty) {
				prefix = ColorsANSI.YELLOW;
				suffix = ColorsANSI.RESET;
			}
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String location = getCurrentDirectory().toString();
				String currentlocation = location.substring(getDeskPlanerDirectory().getParentFile().toString().length() + 1);
				currentlocation = currentlocation.replace("\\", "/");
				System.out.print(prefix + currentlocation + "~ " + suffix);
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
	
	public static File inititalizeDirectory(String path) {
		if(!path.startsWith("\\")) path = "\\" + path;
		File file = new File(getDeskPlanerDirectory().toString() + path);
		if(!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
	public static void openFile(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static File getDeskPlanerDirectory() {
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
	
	public static File getDirectory(String path) {
		if(!path.startsWith("\\")) path = "\\" + path;
		File file = new File(DeskPlaner.getDeskPlanerDirectory().toString() + path);
		if(!file.exists()) {
			return null;
		}
		return file;
	}
	
	public static File[] getFiles(File directory) {
		return directory.listFiles();
	}
	
	public static File[] getFiles(File directory, String ending) {
		return directory.listFiles((dir, name) -> name.toLowerCase().endsWith(ending));
	}
	
	public static void saveStringToFile(File file, String string) {
		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.getParent(), file.getName()), Charset.forName("UTF-8"));
			String lines[] = string.split("\n");
			for (int i = 0; i < lines.length - 1; i++) {
				writer.write(lines[i]);
				writer.newLine();
			}
			writer.write(lines[lines.length - 1]);
			writer.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static String loadStringFromFile(File file) {
		String string = "";
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get(file.getParent(), file.getName()), Charset.forName("UTF-8"));
			String line;
			while((line = reader.readLine()) != null){
				string += line + "\n";
			}
			reader.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return string;
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static void setScene(Scene scene) {
		stage.setScene(scene);
	}
	
	public static Scene getScene() {
		return stage.getScene();
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