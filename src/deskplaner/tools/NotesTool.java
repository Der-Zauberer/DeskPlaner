package deskplaner.tools;

import deskplaner.gui.Card;
import deskplaner.gui.FlowLayout;
import deskplaner.resources.Resource;
import deskplaner.util.Note;
import deskplaner.util.Tool;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class NotesTool extends Tool {
	
	private static FlowLayout layout = new FlowLayout();
	
	public NotesTool() {
		super("Notes");
		Note.initialize(this);
		Note.loadNotes();
		initializeGui();
		reloadGuiNotes();
	}
	
	private void initializeGui() {
		this.setScene(new Scene(layout));
		this.getScene().getStylesheets().add(Resource.getStyleSheet("style.css"));
	}
	
	public void reloadGuiNotes() {
		layout.getFlowPane().getChildren().clear();
		Button btadd = new Button("Add");
		layout.getToolBar().setPadding(new Insets(20, 40, 20 ,40));
		layout.getToolBar().getItems().add(btadd);
		for(Note note : Note.getNotes()) {
			createGuiNote(note);
		}
		btadd.setOnAction(event -> {
			createGuiNote(new Note("Unnamed", "Text here..."));
		});
	}
	
	private Card createGuiNote(Note note) {
		Card card = new Card(note.getName(), note.getText());
		card.setMinWidth(290);
		card.setMaxWidth(600);
		Pane pane = new Pane();
        HBox.setHgrow(pane, Priority.SOMETIMES);
		Button btedit = new Button("Edit");
		Button btdelete = new Button("Delete");
		Button btcancel = new Button("Cancel");
		Button btsave = new Button("Save");
		btedit.setOnAction(event -> {
			card.getToolbar().getItems().clear();
			card.setToolbarColorBlue();
			card.setEditMode();
			card.getToolbar().getItems().addAll(btdelete, pane, btcancel, btsave);
		});
		btsave.setOnAction(event -> {
			card.getToolbar().getItems().clear();
			card.resetToolBarColors();
			Note.getNote(card.setReadMode(true)).set(card.getTitle().getText(), card.getText().getText());
			card.getToolbar().getItems().add(btedit);
		});
		btcancel.setOnAction(event -> {
			card.getToolbar().getItems().clear();
			card.resetToolBarColors();
			card.setReadMode(false);
			card.getToolbar().getItems().add(btedit);
		});
		btdelete.setOnAction(event -> {
			note.removeNote();
			layout.getFlowPane().getChildren().remove(card);
		});
		card.getToolbar().getItems().addAll(btedit);
		layout.getFlowPane().getChildren().add(card);
		return card;
	}

}