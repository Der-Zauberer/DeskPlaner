package deskplaner.resources;

public class Resource {
	
	public static String getStyleSheet(String name) {
		return Resource.class.getResource(name).toExternalForm();
	}

}
