package it.unimi.si.util;

import java.io.File;

public interface ProgramConstant {
	public final String BASE_PATH = "property" + File.separator;
	public final String COMPARATOR = BASE_PATH + "comparator" + File.separator;	
	public final String STORAGE = BASE_PATH + "storage" + File.separator;
	
	public static final String ESECUZIONI_KEY = "esecuzioni";
}
