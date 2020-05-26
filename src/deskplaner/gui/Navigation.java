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
		this.setMinWidth(250);
		this.setMinHeight(200);
		this.setPadding(new Insets(20));
		this.getStyleClass().add("navigation");
		if(title != null && !title.isEmpty()) this.getChildren().add(NodeBuilder.createLabel(title, "title"));
	}
	
	public void setTitle(String title) {
		if(title != null && !title.isEmpty()) {
			for(Node node : this.getChildren()) {
				if(node instanceof Label && node.getStyleClass().contains("title")) {
					if(title != null && !title.isEmpty()) {
						((Label)node).setText(title);
					} else {
						this.getChildren().remove(node);
					}
					return;
				}
			}
			if(title != null && !title.isEmpty()) this.getChildren().add(NodeBuilder.createLabel(title, "title"));
		}
	}
	
	public void addButton(String name, EventHandler<ActionEvent> event) {
		this.getChildren().add(NodeBuilder.createButton(name, event));
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
