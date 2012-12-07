package it.unimi.si;

import it.unimi.si.util.ProgramConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

public class StatisticManager implements ProgramConstant {	
	private final FilenameFilter fnf = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".properties");
		}
	};	

	final Properties properties;
	final File fileComparator;
	
	public StatisticManager() {
		properties = new Properties();
		fileComparator = new File(BASE_PATH + "comparator.properties");
		
		loadData();
		fusionData(analizeComparationData());
		
		//blaaa !!?!
		properties.put(ESECUZIONI_KEY, 
			String.valueOf(
				Integer.parseInt(
					String.valueOf(properties.get(ESECUZIONI_KEY))) + 1
				)
			);
		
		try {
			properties.store(new FileOutputStream(fileComparator), null);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {}
	}

	private void fusionData(Properties tmp) {
		for (Object object : tmp.keySet()) {
			final String exist = (String)properties.get(object);						
			if (exist != null) {
				int a = Integer.valueOf(exist);
				int b = Integer.parseInt(String.valueOf(tmp.get(object)));
				int c = a + b;
				
				properties.put(object, String.valueOf(c));
			} else {
				properties.put(object, String.valueOf((Integer)tmp.get(object)));
			}
		}		
	}

	private void loadData() {
		try {			
			if (!fileComparator.exists()) {
				fileComparator.createNewFile();
			}
			
			properties.load(new FileInputStream(fileComparator));
		} catch (FileNotFoundException e1) {
		} catch (IOException e1) {}
		
		//check default value
		if (properties.get(ESECUZIONI_KEY) == null) {
			properties.put(ESECUZIONI_KEY, 0);
		}		
	}

	private Properties analizeComparationData() {
		final Properties current = new Properties();
		final File storageFiles = new File(COMPARATOR);
		
		for (File file : storageFiles.listFiles(fnf)) {
			final Properties p = new Properties();
			
			try {
				p.load(new FileInputStream(file));
			} catch (FileNotFoundException e) {
			} catch (IOException e) {}
			
			final String name = file.getName();
			final int indexOf = name.indexOf(".");
			current.put(name.substring(0, indexOf), p.keySet().size());
		}
		
		return current;
	}
	
	public Properties getProperties() {
		return properties;
	}	
}
