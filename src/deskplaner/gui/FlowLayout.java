package deskplaner.gui;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FlowLayout extends HBox {
	
	private VBox vbox = new VBox();
	private ScrollPane scrollpane = new ScrollPane();
	private FlowPane flowpane = new FlowPane();
	
	public FlowLayout() {
		this.getChildren().add(new DeskNavigation());
		this.getChildren().add(vbox);
		HBox.setHgrow(vbox, Priority.ALWAYS);
		vbox.getChildren().add(scrollpane);
		vbox.getStyleClass().add("root");
		VBox.setVgrow(scrollpane, Priority.ALWAYS);
		scrollpane.setContent(flowpane);
		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollpane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollpane.setFitToHeight(true);
		scrollpane.setFitToWidth(true);
		scrollpane.getStyleClass().add("root");
		flowpane.getStyleClass().add("root");
		flowpane.setPadding(new Insets(40));
		flowpane.setHgap(20);
		flowpane.setVgap(20);
	}
	
	public FlowPane getFlowPane() {
		return flowpane;
	}

}
