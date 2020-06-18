package deskplaner.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Card extends VBox {
	
	private Label title;
	private Label text;
	
	public Card() {
		this.getStyleClass().add("card");
		this.setMinWidth(220);
		this.setPadding(new Insets(20));
	}
	
	public Card(String title) {
		this();
		setTitle(title);
	}
	
	public Card(String title, String text) {
		this();
		setTitle(title);
		setText(text);
	}
	
	public void setTitle(String title) {
		if(title != null) {
			if(this.title != null) {
				this.title.setText(title);
			} else {
				this.title = new Label(title);
				this.title.getStyleClass().add("title");
				this.getChildren().add(this.title);
			}
		} else {
			if(this.title != null) {
				this.getChildren().remove(this.title);
				this.title = null;
			}
		}
	}
	
	public void setText(String text) {
		if(text != null) {
			if(this.text != null) {
				this.text.setText(text);
			} else {
				this.text = new Label(text);
				this.getChildren().add(this.text);
				if(this.title != null) this.title.setPadding(new Insets(0, 0, 20, 0));
			}
		} else {
			if(this.text != null) {
				this.getChildren().remove(this.text);
				this.text = null;
				if(this.title != null) this.title.setPadding(new Insets(0));
			}
		}
	}
	
	public void setTitle(Label title) {
		this.title = title;
	}
	
	public Label getTitle() {
		return title;
	}
	
	public void setText(Label text) {
		this.text = text;
	}
	
	public Label getText() {
		return text;
	}
	
}
