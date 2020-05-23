package deskplaner.scenes;

import java.io.File;
import java.util.HashMap;
import deskplaner.main.DeskPlaner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Editor extends Scene {
	
	private static HashMap<String, Tab> tabs = new HashMap<>();
	
	private static BorderPane borderpane = new BorderPane();
	private static ToolBar toolbar = new ToolBar();
	private static VBox vbox = new VBox();
	private static TabPane tabpane = new TabPane();
	
	public Editor() {
		super(borderpane);
		borderpane.setTop(toolbar);
		borderpane.setLeft(vbox);
		borderpane.setCenter(tabpane);
		initializeToolBar();
		initializeHBox();
	}

	private void initializeToolBar() {
		addButton("New", event -> {});
		addButton("Open", event -> {});
		addButton("Save", event -> {});
		addButton("Save As", event -> {});
		
	}
	
	private void initializeHBox() {
		vbox.setPrefWidth(250);
		vbox.setPrefHeight(200);
		vbox.setPadding(new Insets(20));
		File files[] = DeskPlaner.getFiles(DeskPlaner.getDirectory("\\home"));
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			Button button = new Button(file.getName());
			button.setPrefHeight(40);
			button.setPrefWidth(210);
			vbox.getChildren().add(button);
			button.setOnAction(event -> {
				if(!tabs.containsKey(file.getName())) addTab(file);
			});
		}
	}
	
	private void addTab(File file) {
		Tab tab = new Tab();
		tab.setText(file.getName());
		tab.setClosable(true);
		tab.setOnCloseRequest(e -> tabs.remove(tab.getText()));
		ScrollPane scrollpane = new ScrollPane();
		tab.setContent(scrollpane);
		TextArea textarea = new TextArea();
		textarea.minWidthProperty().bind(tabpane.minWidthProperty());
		textarea.setMinHeight(800);
		scrollpane.setContent(textarea);
		textarea.setText(DeskPlaner.loadStringFromFile(file));
		tabpane.getTabs().add(tab);
		tabs.put(file.getName(), tab);
	}
	
	private void addButton(String string, EventHandler<ActionEvent> event) {
		Button button = new Button(string);
		button.setOnAction(event);
		toolbar.getItems().add(button);
	}

}
