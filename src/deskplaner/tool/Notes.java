package deskplaner.tool;

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JFrame;
import deskplaner.util.Tool;

public class Notes extends Tool {
	
	private JFrame jframe;

	public Notes() {
		super("Notes");
	}

	@Override
	public boolean onCommand(String label, String[] args, File location) {
		createJFRame();
		return true;
	}

	@Override
	public String getCommandHelp() {
		return "Open the notes window.";
	}
	
	private void createJFRame() {
		jframe = new JFrame("Notes");
		jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setSize(1290, 720);
		jframe.setLocationRelativeTo(null);
		jframe.toFront();
		jframe.requestFocus();
		jframe.setLayout(new BorderLayout());
	}
	
	private File[] getFiles() {
		return getToolDestination().listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
	}
	
}
