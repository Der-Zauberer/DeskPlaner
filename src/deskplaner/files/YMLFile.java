package deskplaner.files;

import java.io.File;
import java.util.HashMap;

public class YMLFile {
	
	private File file;
	private HashMap<String, Object> entries = new HashMap<>();
	
	public YMLFile(File file) {
		this.file = file;
		String lines[] = new FileAssistent(file).readLines();
		if(lines != null) {
			for (int i = 0; i < lines.length; i++) {
				entries.put(lines[i].split(": ")[0], lines[i].split(": ")[1]);
			}
		}
	}
	
	public void set(String key, String value) {
		entries.put(key, value);
	}
	
	public String getString(String key) {
		return entries.get(key).toString();
	}
	
	public void save() {
		String lines[] = new String[entries.size()];
		int i = 0;
		for (String key : entries.keySet()) {
			lines[i] = key + ": " + entries.get(key).toString();
			i++;
		}
		new FileAssistent(file).saveString(lines);
	}

}
