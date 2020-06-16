package deskplaner.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Navigation extends VBox {
	
	public Navigation() {
		this(null);
	}

	public Navigation(String title) {
		this.getStyleClass().add("navigation");
		this.getStyleClass().add("liblue");
		this.setMinWidth(250);
		this.setMinHeight(200);
		this.setPadding(new Insets(20));
		this.getStyleClass().add("navigation");
		if(title != null && !title.isEmpty()) this.getChildren().add(NodeBuilder.createLabel(title, "title"));
	}
	
	public void setTitle(String title) {
		if(title != null && !title.isEmpty()) {
			if(this.getChildren().get(0) != null && this.getChildren().get(0).getStyleClass().contains("title")) {
				((Label) this.getChildren().get(0)).setText(title);
			}
		} else {
			if(this.getChildren().get(0) != null && this.getChildren().get(0).getStyleClass().contains("title")) {
				this.getChildren().remove(0);
			}
		}
	}
	
	public void addButton(String name, EventHandler<ActionEvent> event) {
		Button button = NodeBuilder.createButton(name, event);
		button.setMinHeight(40);
		button.setMinWidth(210);
		this.getChildren().add(button);
	}
	
	public Button getButton(String name) {
		for(Node node : this.getChildren()) {
			if(node instanceof Button && ((Button)node).getText().equals(name)) {
				return (Button)node;
			}
		}
		return null;
	}
	
	public void removeButton(String name) {
		this.getChildren().remove(getButton(name));
	}
	
	public void removeButtons() {
		for(Node node : this.getChildren()) {
			this.getChildren().remove(node);
		}
	}

}
