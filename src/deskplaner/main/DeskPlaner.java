package deskplaner.main;

import java.util.ArrayList;
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
import deskplaner.tools.DashboardTool;
import deskplaner.tools.NotesTool;
import deskplaner.util.Tool;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeskPlaner extends Application {
	
	private static final String NAME = "DeskPlaner";
	private static final String VERSION = "v0.1-beta";
	private static final String[] AUTHORS = {"Der_Zauberer"};
	
	private static Stage stage;
	private static ArrayList<Tool> tools = new ArrayList<>();
	
	
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
		stage.setScene(getTool("Dashboard").getScene());
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
		registerTool(new DashboardTool());
		registerTool(new NotesTool());
		DeskNavigation.updateNavigation();
	}
	
	public static void registerTool(Tool tool) {
		tools.add(tool);
	}
	
	public static Tool getTool(String name) {
		for (Tool tool : tools) {
			if(tool.getName().equals(name)) {
				return tool;
			}
		}
		return null;
	}
	
	public static ArrayList<Tool> getTools() {
		return tools;
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