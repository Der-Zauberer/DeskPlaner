package deskplaner.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileAssistent {
	
	private File file;
	
	public FileAssistent(File file) {
		this.file = file;
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public String readString() {
		String string = "";
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get(file.toString()), Charset.forName("UTF-8"));
			String line;
			while((line = reader.readLine()) != null){
				string += line + "\n";
			}
			reader.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return string;
	}
	
	public String[] readLines() {
		return readString().split("\n");
	}
	
	public void saveString(String string) {
		saveString(string.split("\n"));
	}
	
	public void saveString(String lines[]) {
		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.toString()), Charset.forName("UTF-8"));
			for (int i = 0; i < lines.length - 1; i++) {
				writer.write(lines[i]);
				writer.newLine();
			}
			writer.write(lines[lines.length - 1]);
			writer.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public File getFile() {
		return file;
	}

}
