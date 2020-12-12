package deskplaner.scenes;

import java.io.File;
import java.util.HashMap;
import deskplaner.gui.Navigation;
import deskplaner.gui.NodeBuilder;
import deskplaner.handler.FileHandler;
import deskplaner.main.DeskPlaner;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditorScene extends Scene {
	
	private static BorderPane borderpane = new BorderPane();
	private static ToolBar toolbar = new ToolBar();
	private static Navigation navigation = new Navigation();
	private static TabPane tabpane = new TabPane();
	
	private static TextArea textarea = new TextArea();
	private static HashMap<Tab, File> files = new HashMap<>();
	
	public EditorScene() {
		super(borderpane);		
		borderpane.setTop(toolbar);
		borderpane.setLeft(navigation);
		borderpane.setCenter(tabpane);
		initializeToolBar();
		refreshNavigation();
	}

	private void initializeToolBar() {
		Button buttonNew = new Button("New");
		Button buttonOpen = new Button("Open");
		Button buttonRefresh = new Button("Refresh");
		Button buttonSave = new Button("Save");
		Button buttonSaveAs = new Button("SaveAs");
		Pane pane1 = new Pane();
		Pane pane2 = new Pane();
		Button buttonDelete = new Button("Delete");
		Button buttonRename = new Button("Rename");
		Button buttonRun = new Button("Run");
		buttonNew.setOnAction(event -> {
			addTab("unnamed.txt", "");
		});
		buttonOpen.setOnAction(event -> {
			File file = NodeBuilder.createFileChooser("Open", "").showOpenDialog(DeskPlaner.getStage());
			if(file != null) addTab(file);
		});
		buttonRefresh.setOnAction(event -> {
			refreshNavigation();
			if(!tabpane.getSelectionModel().isEmpty()) {
				if(files.get(tabpane.getSelectionModel().getSelectedItem()) != null) {
					File file = files.get(tabpane.getSelectionModel().getSelectedItem());
					textarea.setText(FileHandler.readString(file));
				}
			}
		});
		buttonSave.setOnAction(event -> {
			if(!tabpane.getSelectionModel().isEmpty()) {
				File file = files.get(tabpane.getSelectionModel().getSelectedItem());
				if(file != null) {
					FileHandler.saveString(file, textarea.getText());
				} else {
					file = new File(FileHandler.createDirectory("\\home").toString() + "\\" + tabpane.getSelectionModel().getSelectedItem().getText());
					FileHandler.saveString(file, textarea.getText());
					files.put(tabpane.getSelectionModel().getSelectedItem(), file);
				}
			}
			refreshNavigation();
		});
		buttonSaveAs.setOnAction(event -> {
			if(!tabpane.getSelectionModel().isEmpty()) {
				File file = NodeBuilder.createFileChooser("Save As", tabpane.getSelectionModel().getSelectedItem().getText()).showSaveDialog(DeskPlaner.getStage());
				if(file != null) FileHandler.saveString(file, textarea.getText());
			}
			refreshNavigation();
		});
		pane1.setPrefWidth(10);
		pane2.setPrefWidth(10);
		buttonDelete.setOnAction(event -> {
			showDeleteDialog();
		});
		buttonRename.setOnAction(event -> {
			showRenameDialog();
		});
		toolbar.getItems().addAll(buttonNew, buttonOpen, buttonRefresh, buttonSave, buttonSaveAs, pane1, buttonDelete, buttonRename, pane2, buttonRun); 
	}
	
	private void refreshNavigation() {
		navigation.getChildren().clear();
		File files[] = FileHandler.createDirectory("\\home").listFiles();
		if(files.length == 0) {
			navigation.getChildren().add(new Label("No files found!"));
		}
		for (int i = 0; i < files.length; i++) {
			if(!files[i].isDirectory()) {
				File file = files[i];
				navigation.addButton(file.getName(), event -> {
					boolean tabexist = false;
					for (Tab tab : tabpane.getTabs()) {
						if(EditorScene.files.get(tab) !=null && EditorScene.files.get(tab).equals(file)) {
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
		files.put(tab, file);
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
		files.put(tab, null);
		tab.setOnCloseRequest(event -> {
			files.remove(tab);
		});
		return tab;
	}
	
	private void showDeleteDialog() {
		if(!tabpane.getSelectionModel().isEmpty() && files.get(tabpane.getSelectionModel().getSelectedItem()) != null) {
			File file = files.get(tabpane.getSelectionModel().getSelectedItem());
			Label label = new Label("Are you sure you want to delete this file? " + file.getName());
			label.setPadding(new Insets(20));
			Button buttonOk = new Button("Ok");
			Button buttonCancel = new Button("Cancel");
			Pane pane = new Pane();
			HBox.setHgrow(pane, Priority.SOMETIMES);
			ToolBar toolbar = new ToolBar();
			toolbar.getItems().addAll(pane, buttonCancel, buttonOk);
			VBox vbox = new VBox();
			vbox.getChildren().addAll(label, toolbar);
			Scene scene = new Scene(vbox);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			buttonOk.setOnAction(event -> {
				tabpane.getTabs().remove(tabpane.getSelectionModel().getSelectedItem());
				file.delete();
				stage.close();
				refreshNavigation();
			});
			buttonCancel.setOnAction(event -> {
				stage.close();
			});
		}
	}
	
	private void showRenameDialog() {
		if(!tabpane.getSelectionModel().isEmpty()) {
			File file = files.get(tabpane.getSelectionModel().getSelectedItem());
			Label label = new Label("File name:");
			TextField textfield = new TextField();
			textfield.setText(tabpane.getSelectionModel().getSelectedItem().getText());
			Button buttonOk = new Button("Ok");
			Button buttonCancel = new Button("Cancel");
			Pane pane = new Pane();
			HBox.setHgrow(pane, Priority.SOMETIMES);
			ToolBar toolbar = new ToolBar();
			toolbar.getItems().addAll(pane, buttonCancel, buttonOk);
			GridPane gridpane = new GridPane();
			gridpane.setPadding(new Insets(20));
			gridpane.setVgap(10);
			gridpane.add(label, 0, 0);
			gridpane.add(textfield, 0, 1);
			VBox vbox = new VBox();
			vbox.getChildren().addAll(gridpane, toolbar);
			Scene scene = new Scene(vbox);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			buttonOk.setOnAction(event -> {
				if(!textfield.getText().isBlank()) {
					if(file != null) {
						FileHandler.saveString(file, textarea.getText());
						String path = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length());
						File newfile = new File(path + textfield.getText());
						file.renameTo(newfile);
						files.put(tabpane.getSelectionModel().getSelectedItem(), newfile);
					}
					tabpane.getSelectionModel().getSelectedItem().setText(textfield.getText());
					refreshNavigation();
					stage.close();
				}
			});
			buttonCancel.setOnAction(event -> {
				stage.close();
			});
		}
	}

}
