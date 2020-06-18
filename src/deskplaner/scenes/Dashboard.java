package deskplaner.scenes;

import deskplaner.gui.Card;
import deskplaner.gui.DeskNavigation;
import deskplaner.gui.NodeBuilder;
import deskplaner.main.DeskPlaner;
import deskplaner.resources.Resource;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Dashboard extends Scene {

	private static BorderPane borderpane = new BorderPane();
	private static GridPane gridpane = new GridPane();
	
	public Dashboard() {
		super(borderpane);
		borderpane.setLeft(new DeskNavigation());
		borderpane.setCenter(gridpane);
		gridpane.setPadding(new Insets(40));
		gridpane.setVgap(20);
		gridpane.setHgap(20);
		
		gridpane.add(NodeBuilder.createButton("Editor", event -> DeskPlaner.setScene(new Editor())), 0, 0);
		gridpane.add(new Card("Test", "Text"), 0, 1);
		gridpane.add(new Card("text", null), 0, 3);
		
		this.getStylesheets().add(Resource.getStyleSheet("style.css"));
	}
	
}
