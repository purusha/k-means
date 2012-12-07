package it.unimi.si.gui;

import it.unimi.si.util.ProgramConstant;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;

public class MainFrameWindowListener extends WindowAdapter implements ProgramConstant {
	private final FilenameFilter fnf = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.startsWith("[") && name.endsWith("]");
		}
	};
	
	@Override
	public void windowClosing(WindowEvent e) {	    	
		cleanHistory();
		
		System.exit(0);
	}

	public void cleanHistory() {
		final File storageFiles = new File(STORAGE);
		final File[] listFiles = storageFiles.listFiles(fnf);
		
		for (File file : listFiles) {
			file.delete();
		}
	}
}
