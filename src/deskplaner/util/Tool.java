package deskplaner.util;

import java.io.File;

import deskplaner.handler.FileHandler;
import javafx.scene.Scene;

public abstract class Tool {
	
	private String name;
	private File directory;
	private Scene scene;
	
	public Tool(String name) {
		this.name = name;
		directory = FileHandler.createDirectory("\\tools\\" + name);
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public boolean isSceneSet() {
		if(scene != null) return true;
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public File getToolDirectory() {
		return directory;
	}
	
	public File[] getFiles() {
		return directory.listFiles();
	}
	
	public File[] getFiles(String ending) {
		return directory.listFiles((dir, name) -> name.toLowerCase().endsWith(ending));
	}
	
	public Scene getScene() {
		return scene;
	}

}
