package deskplaner.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class Card extends VBox {
	
	private VBox top;
	private VBox bottom;
	private Label title;
	private Label text;
	private ToolBar toolbar;
	
	public Card() {
		this.getStyleClass().add("card");
		this.setMinWidth(220);		
		top = new VBox();
		this.getChildren().add(top);
		top.getStyleClass().add("card-top");
		top.setPadding(new Insets(20));
		bottom = new VBox();
		this.getChildren().add(bottom);
		bottom.getStyleClass().add("card-bottom");
		
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
				top.getChildren().add(this.title);
			}
		} else {
			if(this.title != null) {
				top.getChildren().remove(this.title);
				this.title = null;
			}
		}
	}
	
	public void setTitle(Label title) {
		this.title = title;
	}
	
	public Label getTitle() {
		return title;
	}
	
	public void setText(String text) {
		if(text != null) {
			if(this.text != null) {
				this.text.setText(text);
			} else {
				this.text = new Label(text);
				top.getChildren().add(this.text);
				if(this.title != null) this.title.setPadding(new Insets(0, 0, 20, 0));
			}
		} else {
			if(this.text != null) {
				top.getChildren().remove(this.text);
				this.text = null;
				if(this.title != null) this.title.setPadding(new Insets(0));
			}
		}
	}
	
	public void setText(Label text) {
		this.text = text;
	}
	
	public Label getText() {
		return text;
	}
	
	public void initializeToolBar() {
		toolbar = new ToolBar();
		bottom.getChildren().add(toolbar);
		top.getStyleClass().add("top-radius");
	}
	
	public void removeToolBar() {
		bottom.getChildren().remove(toolbar);
		top.getStyleClass().remove("top-radius");
		toolbar = null;
	}
	
	public ToolBar getToolbar() {
		return toolbar;
	}
	
}
