package deskplaner.scenes;

import deskplaner.gui.Navigation;
import deskplaner.gui.NodeBuilder;
import deskplaner.main.DeskPlaner;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Dashboard extends Scene {

	private static BorderPane borderpane = new BorderPane();
	private static VBox vbox = new VBox();
	private static Navigation navigation = new Navigation("DeskPlaner");
	
	public Dashboard() {
		super(borderpane);
		navigation.addButton("Dashboard", event -> {});
		borderpane.setLeft(navigation);
		borderpane.setCenter(vbox);
		vbox.setPadding(new Insets(40));
		vbox.getChildren().add(NodeBuilder.createButton("Editor", event -> DeskPlaner.setScene(new Editor())));
	}
	
}
