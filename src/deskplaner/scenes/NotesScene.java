package deskplaner.scenes;

import java.util.ArrayList;
import deskplaner.gui.Card;
import deskplaner.gui.FlowLayout;
import deskplaner.resources.Resource;
import deskplaner.util.Note;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class NotesScene extends Scene {
	
	private static FlowLayout layout = new FlowLayout();
	
	public NotesScene() {
		super(layout);
		Note.loadNotes();
		initializeGui();
		reloadGuiNotes();
	}
	
	private void initializeGui() {
		this.getStylesheets().add(Resource.getStyleSheet("style.css"));
	}
	
	public void reloadGuiNotes() {
		layout.getFlowPane().getChildren().clear();
		Button btadd = new Button("Add");
		TextField tfsearch = new TextField();
		tfsearch.setText("Search");
		layout.getToolBar().setPadding(new Insets(20, 40, 20 ,40));
		layout.getToolBar().getItems().addAll(btadd, tfsearch);
		for(Note note : Note.getNotes()) {
			createGuiNote(note);
		}
		btadd.setOnAction(event -> {
			if(Note.getNote("Unnamed") == null) {
				createGuiNote(new Note("Unnamed", "Text here..."));
			} else {
				int i = 1;
				while (Note.getNote("Unnamed" + i) != null) {
					i++;
				}
				createGuiNote(new Note("Unnamed" + i, "Text here..."));
			}
		});
		tfsearch.setOnAction(event -> {
			ArrayList<Node> nodes = new ArrayList<>();
			for(Node node : layout.getFlowPane().getChildren()) {
				if(node instanceof Card) {
					nodes.add(node);
				}
			}
			for (Node node : nodes) {
				layout.getFlowPane().getChildren().remove(node);
			}
			if(tfsearch.getText().equals("")) {
				for(Note note : Note.getNotes()) {
					createGuiNote(note);
				}
			} else {
				for(Note note : Note.getNotes()) {
					if(note.getName().contains(tfsearch.getText())) {
						createGuiNote(note);
					}
				}
			}
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