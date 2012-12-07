package it.unimi.si.util;

public class ActionCommandSplitter {
	
	private final String actionCommand;
	
	public ActionCommandSplitter(String aC) {
		actionCommand = aC;
	}
	
	public int getNumber() {
		final String tmp = actionCommand.split("-")[1];
		
		return Integer.parseInt(tmp.substring(0, tmp.length() - 1));
	}
	
	public char getMouseButton() {
		final String tmp = actionCommand.split("-")[0];
		
		return tmp.charAt(1);
	}
}
