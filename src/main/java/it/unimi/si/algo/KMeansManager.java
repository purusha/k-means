package it.unimi.si.algo;

import it.unimi.si.algo.metric.KMeansMetric;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KMeansManager {
	protected final List<Point> centroids;
	protected final List<Point> points;
	protected Map<Point, List<Point>> association;
	
	private final KMeansMetric metric;

	public KMeansManager(KMeansMetric type) {
		centroids = new ArrayList<Point>();
		points = new ArrayList<Point>();
		association = new HashMap<Point, List<Point>>();
		
		metric = type;
	}

	private Point calculateNearCentroid(Point point) {
		Point nearCentroid = null;
		double min = Double.MAX_VALUE;
		
		for (Point centroid : centroids) {			
			double distance = metric.calculatedistance(point, centroid);

			if (distance < min) {
				min = distance;
				nearCentroid = centroid;
			}
		}
		
		return nearCentroid;
	}

	private Point calculateNewCentroidPosition(List<Point> pointList) {
		double x = 0;
		double y = 0;
		
		for (Point point : pointList) {
			x += point.getX();
			y += point.getY();			
		}
		
		final Point p = new Point();
		p.setLocation(x / pointList.size(), y / pointList.size());
		
		return p;
	}
	
	public void pointClassification() {
		association = new HashMap<Point, List<Point>>();
		
		for (Point point : points) {
			final Point centroid = calculateNearCentroid(point);
			
			if (association.get(centroid) == null) {
				association.put(centroid, new ArrayList<Point>());
			} 
			
			final List<Point> pointList = association.get(centroid);
			pointList.add(point);
		}
	}

	public void centroidUpdate() {
		for (Point centroid : centroids) {
			final List<Point> pointList = association.get(centroid);
			
			if (pointList != null) {
				centroid.setLocation(calculateNewCentroidPosition(pointList));
				/*
				 * OCHO !!?
				 * 
				 * la setLocation cambia la rappresentazione interna dell'oggetto
				 * quindi i points ad esso associati si perdono ... ma in fondo è quello che voglio !!?
				 */
			}
		}
	}	
	
	public void addCentroid(Point point) {
		centroids.add(point);
	}

	public void addPoint(Point point) {
		points.add(point);
	}

	public List<Point> getPoints() {
		return Collections.unmodifiableList(points);
	}

	public List<Point> getCentroids() {
		return Collections.unmodifiableList(centroids);
	}

	public List<Point> getPointsOf(Point centroid) {
		final List<Point> list = association.get(centroid);
		
		return Collections.unmodifiableList(list == null ? new ArrayList<Point>() : list);
	}	
}
