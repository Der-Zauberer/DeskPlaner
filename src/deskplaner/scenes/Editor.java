package deskplaner.scenes;

import java.io.File;
import deskplaner.main.DeskPlaner;
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
		addTab("file.txt");
		addTab("test.txt");
	}

	private void initializeToolBar() {
		toolbar.getItems().add(new Button("New"));
		toolbar.getItems().add(new Button("Open"));
		toolbar.getItems().add(new Button("Save"));
		toolbar.getItems().add(new Button("Save As"));
	}
	
	private void initializeHBox() {
		vbox.setPrefWidth(250);
		vbox.setPrefHeight(200);
		vbox.setPadding(new Insets(20));
		File files[] = DeskPlaner.getFiles(DeskPlaner.getDirectory("\\home"));
		for (int i = 0; i < files.length; i++) {
			Button button = new Button(files[i].getName());
			button.setPrefHeight(40);
			button.setPrefWidth(210);
			vbox.getChildren().add(button);
		}
	}
	
	private void addTab(String name) {
		Tab tab = new Tab();
		tab.setText(name);
		tab.setClosable(true);
		ScrollPane scrollpane = new ScrollPane();
		tab.setContent(scrollpane);
		TextArea textarea = new TextArea();
		textarea.minWidthProperty().bind(tabpane.minWidthProperty());
		textarea.setMinHeight(800);
		scrollpane.setContent(textarea);
		tabpane.getTabs().add(tab);
	}

}
