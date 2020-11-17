package deskplaner.tools;

import java.io.File;
import java.util.HashMap;
import deskplaner.gui.Navigation;
import deskplaner.gui.NodeBuilder;
import deskplaner.handler.FileHandler;
import deskplaner.main.DeskPlaner;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class EditorScene extends Scene {
	
	private static BorderPane borderpane = new BorderPane();
	private static ToolBar toolbar = new ToolBar();
	private static Navigation navigation = new Navigation();
	private static TabPane tabpane = new TabPane();
	
	private static TextArea textarea = new TextArea();
	private static HashMap<Integer, File> files = new HashMap<>();
	
	public EditorScene() {
		super(borderpane);		
		borderpane.setTop(toolbar);
		borderpane.setLeft(navigation);
		borderpane.setCenter(tabpane);
		initializeToolBar();
		refreshNavigation();
	}

	private void initializeToolBar() {
		toolbar.getItems().add(NodeBuilder.createButton("New", event -> {
			addTab("unnamed.txt", "");
		}));
		toolbar.getItems().add(NodeBuilder.createButton("Open", event -> {
			File file = NodeBuilder.createFileChooser("Open", "").showSaveDialog(DeskPlaner.getStage());
			if(file != null) addTab(file);
		}));
		toolbar.getItems().add(NodeBuilder.createButton("Refresh", event -> {
			if(!tabpane.getSelectionModel().isEmpty()) {
				File file = files.get(tabpane.getSelectionModel().getSelectedIndex());
				textarea.setText(FileHandler.readString(file));
			}
			refreshNavigation();
		}));
		toolbar.getItems().add(NodeBuilder.createButton("Save", event -> {
			if(!tabpane.getSelectionModel().isEmpty()) {
				File file = files.get(tabpane.getSelectionModel().getSelectedIndex());
				FileHandler.saveString(file, textarea.getText());
			}
			if(!tabpane.getSelectionModel().isEmpty()) {
				File file = files.get(tabpane.getSelectionModel().getSelectedIndex());
				textarea.setText(FileHandler.readString(file));
			}
		}));
		toolbar.getItems().add(NodeBuilder.createButton("Save As", event -> {
			if(!tabpane.getSelectionModel().isEmpty()) {
				File file = NodeBuilder.createFileChooser("Save As", tabpane.getSelectionModel().getSelectedItem().getText()).showSaveDialog(DeskPlaner.getStage());
				if(file != null) FileHandler.saveString(file, textarea.getText());
			}
		}));
		
	}
	
	private void refreshNavigation() {
		navigation.getChildren().clear();
		File files[] = FileHandler.createDirectory("\\home").listFiles();
		for (int i = 0; i < files.length; i++) {
			if(!files[i].isDirectory()) {
				File file = files[i];
				navigation.addButton(file.getName(), event -> {
					boolean tabexist = false;
					for (Tab tab : tabpane.getTabs()) {
						if(tab.getText().equals(file.getName())) {
							tabpane.getSelectionModel().select(tab);
							textarea.setText(FileHandler.readString(file));
							tabexist = true;
						}
					}
					if(!tabexist) addTab(file);
				});
			}
		}
	}
	
	private static void addTab(File file) {
		Tab tab = addTab(file.getName(), FileHandler.readString(file));
		files.put(tabpane.getTabs().indexOf(tab), file);
	}
	
	private static Tab addTab(String name, String text) {
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
		tab.setOnSelectionChanged(event -> {EditorScene.textarea = textarea;});
		tabpane.getTabs().add(tab);
		tabpane.getSelectionModel().select(tab);
		files.put(tabpane.getTabs().indexOf(tab), new File(FileHandler.createDirectory("\\home").toString() + "\\unnamed.txt"));
		tab.setOnCloseRequest(event -> {
			files.remove(tabpane.getTabs().indexOf(tab));
		});
		return tab;
	}

}
