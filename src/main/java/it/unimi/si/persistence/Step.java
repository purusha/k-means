package it.unimi.si.persistence;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Step implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Point> points = new ArrayList<Point>();
	private List<Point> centroids = new ArrayList<Point>();
	private Map<Point, List<Point>> associations = new HashMap<Point, List<Point>>();
	
	public Step() {
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	public List<Point> getCentroids() {
		return centroids;
	}
	
	public void setCentroids(List<Point> centroids) {
		this.centroids = centroids;
	}
	
	public Map<Point, List<Point>> getAssociations() {
		return associations;
	}
	
	public void setAssociations(Map<Point, List<Point>> associations) {
		this.associations = associations;
	}
}
