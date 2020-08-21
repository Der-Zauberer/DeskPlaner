package deskplaner.tools;

import java.io.File;
import java.util.ArrayList;
import deskplaner.gui.Card;
import deskplaner.gui.FlowLayout;
import deskplaner.handler.FileHandler;
import deskplaner.resources.Resource;
import deskplaner.util.Tag;
import deskplaner.util.Tool;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Notes extends Tool {
	
	private static FlowLayout layout = new FlowLayout();
	
	public Notes() {
		super("Notes");
		loadNotes();
		initializeGui();
		reloadGuiNotes();
	}
	
	private void initializeGui() {
		this.setScene(new Scene(layout));
		this.getScene().getStylesheets().add(Resource.getStyleSheet("style.css"));
	}
	
	public void reloadGuiNotes() {
		layout.getFlowPane().getChildren().clear();
		for(String name : getNotes()) {
			Card card = new Card(name, getNoteText(name));
			card.initializeToolBar();
			card.getToolbar().getItems().add(new Button("Edit"));
			layout.getFlowPane().getChildren().add(card);
		}
	}
	
	private void loadNotes() {
		for(File file : this.getFiles()) {
			String name = file.getName().substring(0, file.getName().length() - 4);
			String text = FileHandler.readString(file);
			addNote(name, text);
		}
	}
	
	private void saveNote(String oldname, String newname, String text) {
		for(File file : getFiles()) {
			if(file.getName().equals(oldname + ".txt")) {
				File newfile = new File(this.getToolDirectory() + "\\" + newname + ".txt");
				FileHandler.saveString(newfile, text);
				file.delete();
				return;
			}
		}
		FileHandler.saveString(new File(this.getToolDirectory() + "\\" + newname + ".txt"), text);
	}
	
	public void addNote(String name, String text) {
		if(Note.getNote(name) == null) {
			new Note(name, text);
			saveNote(null, name, text);
		}
	}
	
	public void editNote(String oldname, String newname, String text) {
		if(Note.getNote(newname) == null) {
			Note note = Note.getNote(oldname);
			note.setName(newname);
			note.setText(text);
			saveNote(oldname, newname, text);
		}
	}
	
	public String getNoteText(String name) {
		return Note.getNote(name).getText();
	}
	
	public void addNoteTag(String name, Tag tag) {
		if(!Note.getNote(name).getTags().contains(tag)) {
			Note.getNote(name).getTags().add(tag);
		}
	}
	
	public void removeNoteTag(String name, Tag tag) {
		Note.getNote(name).getTags().remove(tag);
	}
	
	public ArrayList<Tag> getNoteTags(String name) {
		return Note.getNote(name).getTags();
	}
	
	public ArrayList<String> getNotes() {
		ArrayList<String> notes = new ArrayList<String>();
		for(Note note : Note.getNotes()) {
			notes.add(note.getName());
		}
		return notes;
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
	
	public static Note getNote(String name) {
		for(Note note : notes) {
			if(note.getName().equals(name)) {
				return note;
			}
		}
		return null;
	}
	
	public static ArrayList<Note> getNotes() {
		return notes;
	}
	
}