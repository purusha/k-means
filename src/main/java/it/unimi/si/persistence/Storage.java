package it.unimi.si.persistence;

import it.unimi.si.PreferencesKey;
import it.unimi.si.PreferencesManager;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {	
	private Map<String, Step> steps = null;
	private boolean memoryVsFile;

	public Storage() {
		steps = new HashMap<String, Step>();
		
		final String persistence = PreferencesManager.getInstance().get(PreferencesKey.PERSISTENCE);
		System.out.println("Persistence: " + persistence);
		memoryVsFile = (PersistenceType.MEMORY.name().equals(persistence));
	}
	
	public void save(String actionCommand, List<Point> points, List<Point> centroids, Map<Point, List<Point>> association) {		
		final Step st = new Step();
		
		st.setPoints(copy(points));
		st.setCentroids(copy(centroids));
		st.setAssociations(copy(association));
		
		if (memoryVsFile) {
			saveOnMemory(actionCommand, st);
		} else {
			saveOnDisk(actionCommand, st);					
		}
	}

	private void saveOnDisk(String actionCommand, final Step st) {
		try {
			new ObjectStepSerializer(actionCommand).write(st);
		} catch (Exception e) {}
	}

	private void saveOnMemory(String actionCommand, final Step st) {
		steps.put(actionCommand, st);
	}

	public void retrive(String actionCommand, List<Point> points, List<Point> centroids, Map<Point, List<Point>> association) {		
		final Step st;
		
		if (memoryVsFile) {
			st = retriveFromMemory(actionCommand);
		} else {
			st = retriveFromDisk(actionCommand);		
		}		
		
		points.clear();
		points.addAll(st.getPoints());
		
		centroids.clear();
		centroids.addAll(st.getCentroids());
		
		association.clear();		
		association.putAll(st.getAssociations());
	}

	private Step retriveFromDisk(String actionCommand) {
		try {
			return new ObjectStepSerializer(actionCommand).read();
		} catch (Exception e) {
			return new Step();
		}
	}

	private Step retriveFromMemory(String actionCommand) {
		return steps.get(actionCommand);
	}	

	private Map<Point, List<Point>> copy(Map<Point, List<Point>> src) {
		final Map<Point, List<Point>> dest = new HashMap<Point, List<Point>>();
		
		for (Point k : src.keySet()) {
			dest.put(new Point(k.x, k.y), copy(src.get(k)));			
		}
		
		return dest;
	}

	private List<Point> copy(List<Point> src) {
		final List<Point> dest = new ArrayList<Point>();
		
		if (src != null) {
			for(Point point : src) {
				dest.add(new Point(point.x, point.y));
			}			
		}
		
		return dest; 
	}
}
