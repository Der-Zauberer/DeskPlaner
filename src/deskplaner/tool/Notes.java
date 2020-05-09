package deskplaner.tool;

import java.io.File;

import javax.swing.JFrame;

import deskplaner.util.Tool;

public class Notes extends Tool {

	public Notes() {
		super("Notes");
	}

	@Override
	public boolean onCommand(String label, String[] args, File location) {
		JFrame jframe = new JFrame("Notes");
		jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setSize(1290, 720);
		jframe.setLocationRelativeTo(null);
		jframe.toFront();
		jframe.requestFocus();
		return true;
	}

	@Override
	public String getCommandHelp() {
		return null;
	}
	
}
