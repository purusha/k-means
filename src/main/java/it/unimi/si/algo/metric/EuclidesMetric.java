package it.unimi.si.algo.metric;


import java.awt.Point;

public class EuclidesMetric implements KMeansMetric {
	
	@Override
	public double calculatedistance(Point a, Point b) {
		return Math.sqrt(
			Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2)
		);
	}

	@Override
	public String getName() {
		return "Euclide";
	}
	
}
