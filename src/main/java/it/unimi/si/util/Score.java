package it.unimi.si.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Score extends Properties implements ProgramConstant {
	private static final long serialVersionUID = 1L;
	
	private FileOutputStream fos;
	private int enable = 0;
	
	public Score(String fileName) {
		final File file = new File(COMPARATOR + fileName + ".properties");
		
		if (file.exists()) {
			file.delete();
		}
		
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {}		
	}
	
	public void storeAll() {
		if (enable <= 1) {
			try {
				this.store(fos, null); 
			} catch (IOException e) {}			
			
			if (enable == 1) {
				enable = 2;
			}			
		}
	}

	public void endOfPlay() {
		if (enable == 0) {
			enable = 1;
		}
	}
}
