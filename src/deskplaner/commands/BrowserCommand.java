package deskplaner.commands;

import java.io.File;
import deskplaner.handler.CommandHandler;
import deskplaner.handler.FileHandler;
import deskplaner.util.Command;

public class BrowserCommand implements Command{

	@Override
	public boolean onCommand(String label, String[] args, File directory) {
		if(args.length == 0) {
			CommandHandler.sendConsoleOutput(getCommandHelp());
			return false;
		}
		if (args.length >= 1) {
			if(!CommandHandler.hasCondition(args, "-np") && !args[0].startsWith("http://") && !args[0].startsWith("https://")) {
				if(FileHandler.hasVariable(args[0])) {
					FileHandler.openWebsiteInBrowser(FileHandler.getVariable(args[0]));
				} else {
					FileHandler.openWebsiteInBrowser("https://" + args[0]);
				}
			} else {
				FileHandler.openWebsiteInBrowser(args[0]);
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
