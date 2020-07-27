package deskplaner.tools;

import java.io.File;
import deskplaner.files.FileAssistent;
import deskplaner.gui.Card;
import deskplaner.gui.DeskLayout;
import deskplaner.resources.Resource;
import deskplaner.util.Tool;
import javafx.scene.Scene;

public class Notes extends Tool {
	
	private static DeskLayout layout = new DeskLayout();
	
	public Notes() {
		super("Notes");
		initzializeGui();
	}
	
	private void initzializeGui() {
		this.setScene(new Scene(layout));
		this.getScene().getStylesheets().add(Resource.getStyleSheet("style.css"));
		
		for(File file : this.getFiles()) {
			String name = file.getName().substring(0, file.getName().length() - 4);
			String content = new FileAssistent(file).readString();
			Card card = new Card(name, content);
			layout.getFlowPane().getChildren().add(card);
		}
	}

}
