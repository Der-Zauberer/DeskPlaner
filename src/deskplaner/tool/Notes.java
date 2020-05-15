package deskplaner.tool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import deskplaner.util.Tool;

public class Notes extends Tool {
	
	private JFrame jframe;
	private ArrayList<File> files;

	public Notes() {
		super("Notes");
	}

	@Override
	public boolean onCommand(String label, String[] args, File location) {
		files = new ArrayList<File>();
		createJFrame();
		return true;
	}

	@Override
	public String getCommandHelp() {
		return "Open the notes window.";
	}
	
	private void createJFrame() {
		jframe = new JFrame("Notes");
		jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setSize(1290, 720);
		jframe.setLocationRelativeTo(null);
		jframe.toFront();
		jframe.requestFocus();
		jframe.setLayout(new BorderLayout());
		createJFrameContent();
	}
	
	private void createJFrameContent() {
		for (int i = 0; i < getFiles().length; i++) {
			files.add(getFiles()[i]);
		}
		String string[] = new String[files.size()];
		for(int i = 0; i < files.size(); i++) {
			string[i] = files.get(i).getName().substring(0, files.get(i).getName().length() - 3);
		}
		JList<String> jlist = new JList<>(string);
		jlist.setPreferredSize(new Dimension(200, 100));
		JTextArea jtextarea = new JTextArea();
		jframe.getContentPane().add(jlist, BorderLayout.LINE_START);
		jframe.getContentPane().add(jtextarea, BorderLayout.CENTER);
	}
	
	private File[] getFiles() {
		return getToolDestination().listFiles((dir, name) -> name.toLowerCase().endsWith(".md"));
	}
	
}
