package deskplaner.tools;

import java.io.File;
import deskplaner.files.FileAssistent;
import deskplaner.gui.Card;
import deskplaner.gui.DeskNavigation;
import deskplaner.resources.Resource;
import deskplaner.util.Tool;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class Notes extends Tool {
	
	private static BorderPane borderpane = new BorderPane();
	private static ScrollPane scrollpane = new ScrollPane();
	private static FlowPane flowpane = new FlowPane();
	
	public Notes() {
		super("Notes");
		initzializeGui();
	}
	
	private void initzializeGui() {
		this.setScene(new Scene(borderpane));
		this.getScene().getStylesheets().add(Resource.getStyleSheet("style.css"));
		
		borderpane.setLeft(new DeskNavigation());
		borderpane.setCenter(scrollpane);
		scrollpane.setContent(flowpane);
		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollpane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollpane.getStyleClass().add("root");
		flowpane.prefWidthProperty().bind(scrollpane.widthProperty());
		flowpane.getStyleClass().add("root");
		
		flowpane.setPadding(new Insets(40));
		flowpane.setHgap(20);
		flowpane.setVgap(20);
		
		for(File file : this.getFiles()) {
			String name = file.getName().substring(0, file.getName().length() - 4);
			String content = new FileAssistent(file).readString();
			Card card = new Card(name, content);
			flowpane.getChildren().add(card);
		}
	}

}
