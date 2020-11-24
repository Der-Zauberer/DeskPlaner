package deskplaner.commands;

import java.io.File;
import deskplaner.handler.CommandHandler;
import deskplaner.util.Command;
import deskplaner.util.Note;

public class NoteCommand implements Command {

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		if(args.length == 0) {
			CommandHandler.sendConsoleOutput(getCommandHelp());
		} else if(CommandHandler.getCondition(args, "add", 0)) {
			if(args.length == 2) {
				if(Note.getNote(args[1]) == null) {
					new Note(args[1], "");
					CommandHandler.sendConsoleOutput("Added " + args[1] + " note!");
				} else {
					CommandHandler.sendConsoleOutput("The note " + args[1] + " already exists!");
				}
			} else {
				CommandHandler.sendConsoleOutput(args[1] + " is not a valid name!");
			}
		} else if(CommandHandler.getCondition(args, "remove", 0)) {
			if(Note.getNote(args[1]) != null) {
				Note.getNote(args[1]).removeNote();
				CommandHandler.sendConsoleOutput("Removed " + args[1] + " note!");
			} else {
				CommandHandler.sendConsoleOutput("The note " + args[1] + " does not exist!");
			}
		} else if(CommandHandler.getCondition(args, "list", 0)) {
			if(Note.getNotes().size() == 0) {
				CommandHandler.sendConsoleOutput("No notes saved!");
			}
			for(Note note : Note.getNotes()) {
				System.out.println(note.getName());
			}
		}
		return true;
	}

	@Override
	public String getCommandHelp() {
		String string = "note <action> [name] Set or remove a note\n";
		string += "action\tString\t\tUse add, remove or list the notes\n";
		string += "name\tString\t\tThe name of the note";
		return string;
	}

}
