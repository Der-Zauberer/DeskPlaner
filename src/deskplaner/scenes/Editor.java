package deskplaner.scenes;

import java.io.File;
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
import javafx.stage.FileChooser;

public class Editor extends Scene {
	
	private static TextArea textarea = new TextArea();
	
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
		addButton("New", event -> {
			addTab("unnamed.txt", "");
		});
		addButton("Open", event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open");
			File file = fileChooser.showOpenDialog(DeskPlaner.getStage());
			if(file != null) addTab(file);
		});
		addButton("Refresh", event -> {
			File file = new File(DeskPlaner.getDirectory("\\home").toString() + "\\" + tabpane.getSelectionModel().getSelectedItem().getText());
			textarea.setText(DeskPlaner.loadStringFromFile(file));
		});
		addButton("Save", event -> {
			if(!tabpane.getSelectionModel().isEmpty()) {
				File file = new File(DeskPlaner.getDirectory("\\home").toString() + "\\" + tabpane.getSelectionModel().getSelectedItem().getText());
				DeskPlaner.saveStringToFile(file, textarea.getText());
			}
		});
		addButton("Save As", event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save As");
			fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
			fileChooser.setInitialFileName(tabpane.getSelectionModel().getSelectedItem().getText());
			File file = fileChooser.showSaveDialog(DeskPlaner.getStage());
			if(file != null) DeskPlaner.saveStringToFile(file, textarea.getText());
		});
		
	}
	
	private static void initializeHBox() {
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
				boolean tabexist = false;
				for (Tab tab : tabpane.getTabs()) {
					if(tab.getText().equals(button.getText())) {
						tabpane.getSelectionModel().select(tab);
						textarea.setText(DeskPlaner.loadStringFromFile(file));
						tabexist = true;
					}
				}
				if(!tabexist) addTab(file);
			});
		}
	}
	
	private static void addTab(File file) {
		addTab(file.getName(), DeskPlaner.loadStringFromFile(file));
	}
	
	private static void addTab(String name, String text) {
		Tab tab = new Tab();
		tab.setText(name);
		tab.setClosable(true);
		ScrollPane scrollpane = new ScrollPane();
		tab.setContent(scrollpane);
		TextArea textarea = new TextArea();
		textarea.minWidthProperty().bind(tabpane.minWidthProperty());
		textarea.setText(text);
		textarea.setStyle("-fx-font-family: consolas; -fx-font-size: 16px; -fx-text-fill: black;");
		textarea.prefWidthProperty().bind(scrollpane.widthProperty());
		textarea.prefHeightProperty().bind(scrollpane.heightProperty());
		scrollpane.setContent(textarea);
		tab.setOnSelectionChanged(event -> {Editor.textarea = textarea;});
		tabpane.getTabs().add(tab);
		tabpane.getSelectionModel().select(tab);
	}
	
	private static void addButton(String string, EventHandler<ActionEvent> event) {
		Button button = new Button(string);
		button.setOnAction(event);
		toolbar.getItems().add(button);
	}

}
