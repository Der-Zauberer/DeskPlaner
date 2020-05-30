package deskplaner.files;

import java.io.File;
import java.util.ArrayList;

public class CSVFile {
	
	private File file;
	private ArrayList<ArrayList<String>> lines = new ArrayList<>();

	public CSVFile(File file) {
		this.file = file;
		read();
	}
	
	public String getString(int x, int y) {
		return lines.get(y).get(x);
	}
	
	public void setString(String string, int x, int y) {
		lines.get(y).set(x, string);
	}
	
	private void read() {
		this.lines = null;
		String lines[] = new FileAssistent(file).readLines();
		for (int i = 0; i < lines.length; i++) {
			ArrayList<String> line = new ArrayList<>();
			this.lines.add(line);
			String entries[] = lines[i].split(",");
			for (int j = 0; j < entries.length; j++) {
				line.add(entries[j]);
			}
		}
	}
	
	public void save() {
		String lines[] = new String[this.lines.size()];
		for (ArrayList<String> line : this.lines) {
			String string = null;
			for (String entry : line) {
				string += entry + ",";
			}
			if(string.length() > 1) string = string.substring(0, string.length() - 1);
			lines[this.lines.indexOf(line)] = string;
		}
		new FileAssistent(file).saveString(lines);
	}

}
