package deskplaner.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeskStage extends Stage {
	
	public DeskStage(String title)  {
		super();
		this.setHeight(720);
		this.setWidth(1280);
		this.setTitle(title);
	}
	
	public DeskStage(String title, boolean maximized) {
		this(title);
		this.setMaximized(maximized);
	}
	
	public DeskStage(String title, Scene scene) {
		this(title);
		this.setScene(scene);
	}
	
	public DeskStage(String title, Scene scene, boolean maximized) {
		this(title, maximized);
		this.setScene(scene);
	}
	
	public DeskStage(String title, boolean maximized, boolean exitOnClose) {
		this(title, maximized);
		if(exitOnClose) this.setOnCloseRequest(event -> System.exit(0));
	}

}
