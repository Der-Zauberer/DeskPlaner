package deskplaner.scenes;

import deskplaner.gui.DeskNavigation;
import deskplaner.resources.Resource;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Notes extends Scene {
	
	private static BorderPane borderpane = new BorderPane();
	
	public Notes() {
		super(borderpane);
		borderpane.setLeft(new DeskNavigation());
		this.getStylesheets().add(Resource.getStyleSheet("style.css"));
	}

}
