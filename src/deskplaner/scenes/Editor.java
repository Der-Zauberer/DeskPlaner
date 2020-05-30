package deskplaner.scenes;

import java.io.File;

import deskplaner.files.FileAssistent;
import deskplaner.gui.Navigation;
import deskplaner.gui.NodeBuilder;
import deskplaner.main.DeskPlaner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class Editor extends Scene {
	
	private static BorderPane borderpane = new BorderPane();
	private static ToolBar toolbar = new ToolBar();
	private static Navigation navigation = new Navigation();
	private static TabPane tabpane = new TabPane();
	
	private static TextArea textarea = new TextArea();
	
	public Editor() {
		super(borderpane);		
		borderpane.setTop(toolbar);
		borderpane.setLeft(navigation);
		borderpane.setCenter(tabpane);
		initializeToolBar();
		initializeNavigation();
	}

	private void initializeToolBar() {
		addButton("New", event -> {
			addTab("unnamed.txt", "");
		});
		addButton("Open", event -> {
			File file = NodeBuilder.createFileChooser("Open", "").showSaveDialog(DeskPlaner.getStage());
			if(file != null) addTab(file);
		});
		addButton("Refresh", event -> {
			if(!tabpane.getSelectionModel().isEmpty()) {
				File file = new File(DeskPlaner.getDirectory("\\home").toString() + "\\" + tabpane.getSelectionModel().getSelectedItem().getText());
				textarea.setText(new FileAssistent(file).readString());
			}
		});
		addButton("Save", event -> {
			if(!tabpane.getSelectionModel().isEmpty()) {
				File file = new File(DeskPlaner.getDirectory("\\home").toString() + "\\" + tabpane.getSelectionModel().getSelectedItem().getText());
				new FileAssistent(file).saveString(textarea.getText());
			}
		});
		addButton("Save As", event -> {
			if(!tabpane.getSelectionModel().isEmpty()) {
				File file = NodeBuilder.createFileChooser("Save As", tabpane.getSelectionModel().getSelectedItem().getText()).showSaveDialog(DeskPlaner.getStage());
				if(file != null) new FileAssistent(file).saveString(textarea.getText());
			}
		});
		
	}
	
	private void initializeNavigation() {
		File files[] = DeskPlaner.getFiles(DeskPlaner.getDirectory("\\home"));
		for (int i = 0; i < files.length; i++) {
			if(!files[i].isDirectory()) {
				File file = files[i];
				navigation.addButton(file.getName(), event -> {
					boolean tabexist = false;
					for (Tab tab : tabpane.getTabs()) {
						if(tab.getText().equals(file.getName())) {
							tabpane.getSelectionModel().select(tab);
							textarea.setText(new FileAssistent(file).readString());
							tabexist = true;
						}
					}
					if(!tabexist) addTab(file);
				});
			}
		}
	}
	
	private static void addTab(File file) {
		addTab(file.getName(), new FileAssistent(file).readString());
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
