package deskplaner.gui;

import java.util.ArrayList;
import deskplaner.main.DeskPlaner;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class DeskNavigation extends Navigation {
	
	private static ArrayList<DeskNavigation> navigations = new ArrayList<>();
	
	public DeskNavigation() {
		super("DeskPlaner");
		this.getStyleClass().addAll("navigation", "white-text", "liblue");
		navigations.add(this);	
	}
	
	public static void updateNavigation() {
		boolean first = true;
		for (DeskNavigation desknavigation : navigations) {
			for(Node node : desknavigation.getChildren()) {
				if(node instanceof Button) desknavigation.getChildren().remove(node);
			}
			for (String string : DeskPlaner.getScenes().keySet()) {
				desknavigation.addButton(string, event -> {
					DeskPlaner.setScene(DeskPlaner.getScenes().get(string));
					if(!((Button)event.getSource()).getStyleClass().contains("active")) setActiveButton(((Button)event.getSource()).getText());
				});
				if(first) {
					first = false;
					setActiveButton(string);
				}
				
			}
		}
	}
	
	public static void setActiveButton(String string) {
		for(DeskNavigation navigation : navigations) {
			for(Node node : navigation.getChildren()) {
				if(node instanceof Button) {
					if(((Button)node).getText().equals(string)) {
						node.getStyleClass().add("active");
					} else {
						node.getStyleClass().remove("active");
					}
				}
			}
		}
	}

}
