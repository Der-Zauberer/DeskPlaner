package deskplaner.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import deskplaner.commands.BrowserCommand;
import deskplaner.commands.CDCommand;
import deskplaner.commands.HelpCommand;
import deskplaner.commands.LSCommand;
import deskplaner.commands.MkDirCommand;
import deskplaner.commands.RMCommand;
import deskplaner.commands.VariableCommand;
import deskplaner.commands.VersionCommand;
import deskplaner.gui.DeskNavigation;
import deskplaner.gui.DeskStage;
import deskplaner.handler.CommandHandler;
import deskplaner.handler.FileHandler;
import deskplaner.scenes.DashboardScene;
import deskplaner.scenes.NotesScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeskPlaner extends Application {
	
	private static final String NAME = "DeskPlaner";
	private static final String VERSION = "v0.1-beta";
	private static final String[] AUTHORS = {"Der_Zauberer"};
	
	private static Stage stage;
	private static HashMap<String, Scene> scenes = new HashMap<>();
	
	
	public static void main(String[] args) {
		initializeDirectories();
		FileHandler.initializeFileHandler();
		registerCommands();
		console();
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		registerScenes();
		stage = new DeskStage(getName(), false, true);
		DeskPlaner.stage = stage;
		stage.setScene(getScene("Dashboard"));
		stage.show();
	}
	
	@SuppressWarnings("resource")
	private static void console() {
		new Thread(() -> {
			String prefix = "";
			String suffix = "";
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String location = FileHandler.getCurrentDirectory().toString();
				String currentlocation = location.substring(FileHandler.getDeskPlanerDirectory().getParentFile().toString().length() + 1);
				currentlocation = currentlocation.replace("\\", "/");
				System.out.print(prefix + currentlocation + "~ " + suffix);
				CommandHandler.executeCommand(scanner.nextLine());
			}
		}).start();
	}
	
	private static void initializeDirectories() {
		FileHandler.setCurrentDirectory(FileHandler.createDirectory("home"));
		FileHandler.createDirectory("tools");
		FileHandler.createDirectory("system");
	}
	
	private static void registerCommands() {
		CommandHandler.registerCommand("browser", new BrowserCommand());
		CommandHandler.registerCommand("cd", new CDCommand());
		CommandHandler.registerCommand("help", new HelpCommand());
		CommandHandler.registerCommand("ls", new LSCommand());
		CommandHandler.registerCommand("mkdir", new MkDirCommand());
		CommandHandler.registerCommand("rm", new RMCommand());
		CommandHandler.registerCommand("variable", new VariableCommand());
		CommandHandler.registerCommand("version", new VersionCommand());
	}
	
	private static void registerScenes() {
		registerScene("Dashboard", new DashboardScene());
		registerScene("Notes", new NotesScene());
		DeskNavigation.updateNavigation();
	}
	
	public static void registerScene(String name, Scene scene) {
		scenes.put(name, scene);
	}
	
	public static ArrayList<Scene> getScenes() {
		ArrayList<Scene> scenes = new ArrayList<Scene>();
		for (String name : DeskPlaner.scenes.keySet()) {
			scenes.add(DeskPlaner.scenes.get(name));
		}
		return scenes;
	}
	
	public static ArrayList<String> getSceneNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (String name : DeskPlaner.scenes.keySet()) {
			names.add(name);
		}
		return names;
	}
	
	public static Scene getScene(String name) {
		if(scenes.keySet().contains(name)) {
			return scenes.get(name);
		}
		return null;
	}
		
	public static Stage getStage() {
		return stage;
	}
	
	public static void setScene(Scene scene) {
		stage.setScene(scene);
	}
	
	public static Scene getActiveScene() {
		return stage.getScene();
	}
	
	public static String getName() {
		return NAME;
	}
	
	public static String getVersion() {
		return VERSION;
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