package it.unimi.si.algo.metric;

import java.awt.Point;


public class ChessboardMetric implements KMeansMetric {

	@Override
	public double calculatedistance(Point a, Point b) {
		return Math.max(Math.abs(a.x - b.x), Math.abs(a.y - b.y));		
	}	

	@Override
	public String getName() {
		return "ChessBoard";
	}

}
