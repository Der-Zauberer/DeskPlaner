package deskplaner.util;

import java.util.ArrayList;

public class Tag {
	
	private static ArrayList<Tag> tags = new ArrayList<>();
	private String name;
	private String hexcolor;
	
	public Tag(String name, String hexcolor) {
		this.name = name;
		this.hexcolor = hexcolor;
		tags.add(this);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setHexcolor(String hexcolor) {
		this.hexcolor = hexcolor;
	}
	
	public String getHexcolor() {
		return hexcolor;
	}
	
	public static ArrayList<Tag> getTags() {
		return tags;
	}

}
