package it.unimi.si.algo.metric;

import java.awt.Point;

public interface KMeansMetric {
	public double calculatedistance(Point a, Point b);
	
	public String getName();
}
