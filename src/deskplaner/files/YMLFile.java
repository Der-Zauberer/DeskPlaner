package deskplaner.files;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class YMLFile {
	
	private File file;
	private HashMap<String, Object> entries = new HashMap<>();
	
	public YMLFile(File file) {
		this.file = file;
		String lines[] = new FileAssistent(file).readLines();
		if(lines != null && lines.length > 0) {
			for (int i = 0; i < lines.length; i++) {
				String key = lines[i].split(": ", 2)[0];
				String value = lines[i].split(": ", 2)[1];
				entries.put(key, value);
			}
		}
	}
	
	public void set(String key, String value) {
		if(value == null) {
			entries.remove(key);
		} else {
			entries.put(key, value);
		}
	}
	
	public String getString(String key) {
		try {
			return entries.get(key).toString();
		} catch (Exception exception) {
			return null;
		}
	}
	
	public Set<String> getKeyList() {
		return entries.keySet();
	}
	
	public Set<String> getKeyList(String key) {
		Set<String> keyset = new HashSet<String>(); 
		for (String string : entries.keySet()) {
			if(string.startsWith(key)) {
				keyset.add(string);
			}
		}
		return keyset;
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
