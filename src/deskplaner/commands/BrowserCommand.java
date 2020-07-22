package deskplaner.commands;

import java.io.File;
import java.util.Arrays;
import deskplaner.main.DeskPlaner;
import deskplaner.util.Command;

public class BrowserCommand implements Command{

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		if(args.length == 0) {
			DeskPlaner.sendConsoleOutput(getCommandHelp());
			return false;
		}
		if (args.length >= 1) {
			if(!Arrays.asList(args).contains("-np") && !args[0].startsWith("http://") && !args[0].startsWith("https://")) {
				if(DeskPlaner.hasVariable(args[0])) {
					DeskPlaner.openWebsiteInBrowser(DeskPlaner.getVariable(args[0]));
				} else {
					DeskPlaner.openWebsiteInBrowser("https://" + args[0]);
				}
			} else {
				DeskPlaner.openWebsiteInBrowser(args[0]);
			}
			
		}
		return true;
	}

	@Override
	public String getCommandHelp() {
		String string = "browser [url] Open a website in the mainbrowser of the operating system.\n";
		string += "url\tString\t\tThe url to open the website\n";
		string += "url\tString\t\tThe name of the path variable\n";
		string += "-np\tModifier\tBypass automatic https or http protocoll (No Protocoll)";
		return string;
	}
	
}
