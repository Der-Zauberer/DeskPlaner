package deskplaner.files;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import deskplaner.handler.FileHandler;

public class YMLFile {
	
	private File file;
	private HashMap<String, Object> entries = new HashMap<>();
	
	public YMLFile(File file) {
		this.file = file;
		FileHandler.createFile(file);
		read();
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
	
	public void read() {
		String lines[] = FileHandler.readLines(file);
		String key = "";
		if(lines != null && lines.length > 0 && !lines[0].equals("")) {
			for (int i = 0; i < lines.length; i++) {
				int layer = getLayer(lines[i]);
				String name = lines[i].split(":", 2)[0];
				name = name.substring(layer * 4);
				if(key.split("\\.").length > layer && layer != 0) {
					String keys[] = key.split("\\.");
					key = "";
					for (int j = 0; j < layer; j++) {
						if(!key.equals("")) {key += ".";}
						key += keys[j];
					}
				}
				if(!key.equals("")) {key += ".";}
				key += name;
				if(lines[i].split(": ", 2).length > 1) {
					String value = lines[i].split(": ", 2)[1];
					entries.put(key, value);
				}
				
			}
		}
	}
	
	public void save() {
		String lines[] = new String[entries.size()];
		int i = 0;
		for (String key : entries.keySet()) {
			lines[i] = key + ": " + entries.get(key).toString();
			i++;
		}
		FileHandler.saveLines(file, lines);
	}
	
	private int getLayer(String string) {
		int spaces = 0;
		for(int j = 0; j < string.length(); j++) {
			if(string.charAt(j) == ' ') {
				spaces++;
			} else {
				break;
			}
		}
		return spaces / 4;
	}

}
