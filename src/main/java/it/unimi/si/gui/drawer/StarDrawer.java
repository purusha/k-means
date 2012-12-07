package it.unimi.si.gui.drawer;

import it.unimi.si.algo.KMeansManager;
import it.unimi.si.util.DrawConstant;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class StarDrawer extends CentroidDrawer implements DrawConstant {
	
	public StarDrawer(KMeansManager manager) {
		super(manager);
	}

	@Override
	public void drawOn(Graphics graphics) {
		for (Point centroid : manager.getCentroids()) {			
			graphics.setColor(lightGray);
			final List<Point> ps = manager.getPointsOf(centroid);
			for (Point p : ps) {
				graphics.drawLine(
					centroid.x + CENTROID_RADIUS, centroid.y + CENTROID_RADIUS, 
					p.x + POINT_RADIUS, p.y + POINT_RADIUS
				);
			}
			
			graphics.setColor(Color.red);
			graphics.fillOval((int)centroid.getX(), (int)centroid.getY(), CENTROID_DIAMETER, CENTROID_DIAMETER);			
		}
	}
}
