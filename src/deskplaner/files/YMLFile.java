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
		String lines[] = FileHandler.readLines(file);
		if(lines != null && lines.length > 0 && !lines[0].equals("")) {
			String prefix = "";
			for (int i = 0; i < lines.length; i++) {
				String key = lines[i].split(":", 2)[0];
				key = getFullKey(prefix, key);
				prefix = key;
				if(lines[i].split(": ", 2).length == 2) {
					String value = lines[i].split(": ", 2)[1];
					entries.put(key, value);
				}
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
		FileHandler.saveLines(file, lines);
	}
	
	private int getLineSpaces(String string) {
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
	
	private String getFullKey(String prefix, String key) {
		String fullkey = "";
		String keys[];
		if(prefix.split("\\.").length > 0) {
			keys = prefix.split("\\.");
		} else {
			keys = new String[1];
			keys[0] = prefix;
		}
		for(int i = 0; i < getLineSpaces(key); i++) {
			fullkey += keys[i];
			if(i < getLineSpaces(key)) fullkey += ".";
		}
		fullkey += key.substring(getLineSpaces(key) * 4);
		return fullkey;
	}

}
