package deskplaner.files;

import java.io.File;
import java.util.Arrays;
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
				if(key.split("\\.").length >= layer + 1) {
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
		String output = "";
		String lines[] = new String[entries.size()];
		int i = 0;
		for (String key : entries.keySet()) {
			lines[i] = key;
			i++;
		}
		Arrays.sort(lines);
		String key = "";
		for (int j = 0; j < lines.length; j++) {
			String keylist[] = lines[j].split("\\.");
			for (int k = 0; k < keylist.length; k++) {
				if(!containsKey(key, keylist[k])) {
					if(k < keylist.length - 1) {
						output = calculateLayer(k, keylist[k], "", output);
					} else {
						output = calculateLayer(k, keylist[k], entries.get(lines[j]).toString(), output);
					}
				}
			}
			key = lines[j];
		}
		FileHandler.saveString(file, output);
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
	
	private boolean containsKey(String key, String name) {
		String keys[] = key.split("\\.");
		for (int i = 0; i < keys.length; i++) {
			if(keys[i].equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	private String calculateLayer(int layer, String name, String value, String output) {
		if(!output.equals("")) {
			output += "\n";
		}
		for (int i = 0; i < layer; i++) {
			output += "    ";
		}
		if(value.equals("")) {
			output += name + ":";
		} else {
			output += name + ": " + value;
		}
		return output;
	}

}
