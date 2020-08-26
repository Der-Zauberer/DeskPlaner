package deskplaner.tools;

import deskplaner.gui.Card;
import deskplaner.gui.DeskNavigation;
import deskplaner.gui.DeskStage;
import deskplaner.gui.NodeBuilder;
import deskplaner.handler.FileHandler;
import deskplaner.main.DeskPlaner;
import deskplaner.resources.Resource;
import deskplaner.util.Tool;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class DashboardTool extends Tool {

	private static BorderPane borderpane = new BorderPane();
	private static GridPane gridpane = new GridPane();
	
	public DashboardTool() {
		super("Dashboard");
		initializeGui();
		gridpane.add(NodeBuilder.createButton("Editor", event -> DeskPlaner.setScene(new EditorTool())), 0, 0);
		
		Card cardEditor = new Card("Editor");
		cardEditor.setOnMouseClicked(event -> {new DeskStage("Editor", new EditorTool()).show();});
		
		Card cardGithub = new Card("Github", "https://github.com/der-zauberer/deskplaner");
		cardGithub.setOnMouseClicked(event -> {FileHandler.openWebsiteInBrowser("https://github.com/der-zauberer/deskplaner");});
		gridpane.add(cardEditor, 0, 0);
		gridpane.add(cardGithub, 0, 1);
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
