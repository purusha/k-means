package it.unimi.si;

import it.unimi.si.util.ProgramConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PreferencesManager implements ProgramConstant {
	private static PreferencesManager ref;
	
	public static PreferencesManager getInstance() {
		if (ref == null) {
			ref = new PreferencesManager();
		}
		
		return ref;
	}
	
	/*
	 * Object from here ...
	 */
	
	private File file;
	private Properties properties;
	
	private PreferencesManager() {
		file = new File(BASE_PATH + "application.properties");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {}
		}
		
		checkDefault();
	}
	
	private void checkDefault() {
		properties = new Properties();
		
		try {
			properties.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {}
		
		for (PreferencesKey key : PreferencesKey.values()) {
			if (!properties.containsKey(key.name())) {
				properties.put(key.name(), key.getDefaultValue());
			}
		}
		
		saveProperties();
	}

	private void saveProperties() {
		try {
			properties.store(new FileOutputStream(file), null);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {}
	}
	
	public boolean isActive(String key) {
		for(Object v : properties.values()) {
			if(key.equals(v)){
				return true;
			}
		}
		
		return false;
	}		
	
	public String get(PreferencesKey key) {
		return String.valueOf(
			properties.get(key.name())
		);
	}

	public void set(PreferencesKey key, String value) {
		properties.put(key.name(), value);
		
		saveProperties();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new UnsupportedOperationException("don't clone this instance please !!?");
	}
}