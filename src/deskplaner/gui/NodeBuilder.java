package deskplaner.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

public class NodeBuilder {
	
	public static Label createLabel(String name) {
		return new Label(name);
	}
	
	public static Label createLabel(String name, String styleclass) {
		Label label = new Label(name);
		label.getStyleClass().add(styleclass);
		return label;
	}
	
	public static Button createButton(String name, EventHandler<ActionEvent> event) {
		Button button = new Button(name);
		button.setOnAction(event);
		return button;
	}
	
	public static Button createButton(String name, EventHandler<ActionEvent> event, String styleclass) {
		Button button = new Button(name);
		button.setOnAction(event);
		button.getStyleClass().add(styleclass);
		return button;
	}
	
	public static FileChooser createFileChooser(String title, String filename) {
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle(title);
		filechooser.setInitialFileName(filename);
		return filechooser;
	}
	
}
