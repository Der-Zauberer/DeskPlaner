package deskplaner.util;

import java.io.File;
import java.util.ArrayList;
import deskplaner.handler.FileHandler;
import deskplaner.tools.NotesTool;

public class Note {
	
	private static ArrayList<Note> notes = new ArrayList<>();
	private static NotesTool tool;
	private String name;
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	private String text;
	
	public static void initialize(NotesTool tool) {
		Note.tool = tool;
	}
	
	public Note(String name, String text) {
		this.name = name;
		this.text = text;
		notes.add(this);
		saveNote(null, name, text);
	}
	
	public void setName(String name) {
		String oldname = this.name;
		this.name = name;
		saveNote(oldname, name, text);
	}
	
	public String getName() {
		return name;
	}
	
	public void setText(String text) {
		this.text = text;
		saveNote(name, name, text);
	}
	
	public String getText() {
		return text;
	}
	
	public void set(String name, String text) {
		String oldname = this.name;
		this.name = name;
		saveNote(oldname, name, text);
	}
	
	public ArrayList<Tag> getTags() {
		return tags;
	}
	
	public void removeNote() {
		notes.remove(this);
		deleteNote(name);
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
	
	public static void loadNotes() {
		for(File file : tool.getFiles()) {
			String name = file.getName().substring(0, file.getName().length() - 4);
			String text = FileHandler.readString(file);
			new Note(name, text);
		}
	}
	
	private void saveNote(String oldname, String newname, String text) {
		if(oldname != null && !oldname.equals(newname)) {
			for(File file : tool.getFiles()) {
				if(file.getName().equals(oldname + ".txt")) {
					File newfile = new File(tool.getToolDirectory() + "\\" + newname + ".txt");
					FileHandler.saveString(newfile, text);
					file.delete();
				}
			}
		} else {
			FileHandler.saveString(new File(tool.getToolDirectory() + "\\" + newname + ".txt"), text);
		}
	}
	
	private void deleteNote(String name) {
		File file = new File(tool.getToolDirectory() + "\\" + name + ".txt");
		if(file.exists()) file.delete();
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.text;
	}

}
