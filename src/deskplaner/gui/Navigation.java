package deskplaner.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Navigation extends VBox {
	
	private Label title;
	
	public Navigation() {
		this(null);
	}

	public Navigation(String title) {
		this.setMinWidth(250);
		this.setMinHeight(200);
		this.setPadding(new Insets(20));
		setTitle(title);
	}
	
	public void setTitle(String title) {
		if(title != null) {
			if(this.title == null) {
				this.title = new Label(title);
				this.title.getStyleClass().add("title");
				this.title.setPadding(new Insets(20, 0, 40, 10));
				this.getChildren().add(this.title);
			} else {
				this.title.setText(title);
			}
		} else {
			if(this.title != null) {
				this.getChildren().remove(this.title);
				this.title = null;
			}
		}
	}
	
	public Button addButton(String name, EventHandler<ActionEvent> event) {
		Button button = NodeBuilder.createButton(name, event);
		button.setMinHeight(40);
		button.setMinWidth(210);
		this.getChildren().add(button);
		return button;
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
