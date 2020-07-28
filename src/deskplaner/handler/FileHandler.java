package deskplaner.handler;

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
import java.util.HashMap;
import deskplaner.files.YMLFile;
import deskplaner.main.DeskPlaner;

public class FileHandler {
	
	private static YMLFile settings;
	private static File currentdirectory;
	private static HashMap<String, String> variable = new HashMap<>();
	
	public static void initializeFileHandler() {
		File file = new File(createDirectory("system").toString() + "\\settings.yml");
		settings = new YMLFile(file);
		for (String key : settings.getKeyList("variables")) {
			String name = key.split("variables.")[1];
			String value = settings.getString(key);
			addVariable(name, value);
		}
	}
	
	public static void createFile(File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public static void createFile(File directory, String filename) {
		createFile(new File(directory.toString() + filename));
	}
	
	public static String readString(File file) {
		String string = "";
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get(file.toString()), Charset.forName("UTF-8"));
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
	
	public static String[] readLines(File file) {
		return readString(file).split("\n");
	}
	
	public static void saveString(File file, String string) {
		saveLines(file, string.split("\n"));
	}
	
	public static void saveLines(File file, String lines[]) {
		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.toString()), Charset.forName("UTF-8"));
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
	
	public static void openFile(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void openWebsiteInBrowser(String url) {
		try {
			Desktop.getDesktop().browse(new URL(url).toURI());
		} catch (IOException | URISyntaxException exception) {
			exception.printStackTrace();
		}
	}
	
	public static File createDirectory(String path) {
		if(!path.startsWith("\\")) path = "\\" + path;
		File file = new File(getDeskPlanerDirectory().toString() + path);
		if(!file.exists()) {
			file.mkdirs();
		}
		return file;
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
	
	public static void addVariable(String name, String value) {
		if(!variable.containsKey(name)) {
			variable.put(name, value);
			if(settings.getString("variables." + name) == null) {
				settings.set("variables." + name, value);
				settings.save();
			}
		}
	}
	
	public static void removeVariable(String name) {
		if(variable.containsKey(name)) {
			variable.remove(name);
			if(settings.getString("variables." + name) != null) {
				settings.set("variables." + name, null);
				settings.save();
			}
		}
	}
	
	public static void setCurrentDirectory(File directory) {
		FileHandler.currentdirectory = directory;
	}
	
	public static File getCurrentDirectory() {
		return currentdirectory;
	}
	
	public static String getVariable(String name) {
		return variable.get(name);
	}
	
	public static boolean hasVariable(String name) {
		return variable.containsKey(name);
	}
	
	public static HashMap<String, String> getVariables() {
		return variable;
	}

}
