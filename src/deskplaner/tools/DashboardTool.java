package deskplaner.tools;

import deskplaner.gui.Card;
import deskplaner.gui.DeskNavigation;
import deskplaner.gui.NodeBuilder;
import deskplaner.handler.FileHandler;
import deskplaner.main.DeskPlaner;
import deskplaner.resources.Resource;
import deskplaner.util.Tool;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Dashboard extends Tool {

	private static BorderPane borderpane = new BorderPane();
	private static GridPane gridpane = new GridPane();
	
	public Dashboard() {
		super("Dashboard");
		initializeGui();
		gridpane.add(NodeBuilder.createButton("Editor", event -> DeskPlaner.setScene(new Editor())), 0, 0);
		
		Card card = new Card("Github", "https://github.com/der-zauberer/deskplaner");
		card.setOnMouseClicked(event -> {FileHandler.openWebsiteInBrowser("https://github.com/der-zauberer/deskplaner");});
		gridpane.add(card, 0, 2);
		
		gridpane.add(new Card("Test", "Text"), 0, 1);
		gridpane.add(new Card("text", null), 0, 3);
	}

	private void initializeGui() {
		this.setScene(new Scene(borderpane));
		this.getScene().getStylesheets().add(Resource.getStyleSheet("style.css"));
		borderpane.setLeft(new DeskNavigation());
		borderpane.setCenter(gridpane);
		gridpane.setPadding(new Insets(40));
		gridpane.setVgap(20);
		gridpane.setHgap(20);
	}
	
}
