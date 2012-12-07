package it.unimi.si.algo.metric;

import java.awt.Point;


public class ManhattansMetric implements KMeansMetric {
	
	@Override
	public double calculatedistance(Point a, Point b) {
		int maxX = Math.max(a.x, b.x);
		int minX = Math.min(a.x, b.x);
		int x = maxX - minX;
			
		int maxY = Math.max(a.y, b.y);
		int minY = Math.min(a.y, b.y);
		int y = maxY - minY;
			
		return x + y;
	}

	@Override
	public String getName() {
		return "Manhattan";
	}	

}
