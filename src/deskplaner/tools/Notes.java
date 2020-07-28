package deskplaner.tools;

import java.io.File;
import java.util.ArrayList;
import deskplaner.files.FileAssistent;
import deskplaner.gui.Card;
import deskplaner.gui.FlowLayout;
import deskplaner.resources.Resource;
import deskplaner.util.Tag;
import deskplaner.util.Tool;
import javafx.scene.Scene;

public class Notes extends Tool {
	
	private static FlowLayout layout = new FlowLayout();
	
	public Notes() {
		super("Notes");
		initzializeGui();
		loadNotes();
	}
	
	private void initzializeGui() {
		this.setScene(new Scene(layout));
		this.getScene().getStylesheets().add(Resource.getStyleSheet("style.css"));
	}
	
	private void loadNotes() {
		for(File file : this.getFiles()) {
			String name = file.getName().substring(0, file.getName().length() - 4);
			String text = new FileAssistent(file).readString();
			Note note = new Note(name, text);
			Card card = new Card(name, text);
			layout.getFlowPane().getChildren().add(card);
		}
	}

}
class Note {
	
	private static ArrayList<Note> notes = new ArrayList<>();
	private String name;
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	private String text;
	
	public Note(String name, String text) {
		this.name = name;
		this.text = text;
		notes.add(this);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public ArrayList<Tag> getTags() {
		return tags;
	}
	
	public static void removeNote(Note note) {
		notes.remove(note);
	}
	
	public static ArrayList<Note> getNotes() {
		return notes;
	}
	
}
