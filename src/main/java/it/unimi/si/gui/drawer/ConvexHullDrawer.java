package it.unimi.si.gui.drawer;

import it.unimi.si.algo.KMeansManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import megamu.mesh.Hull;

public class ConvexHullDrawer extends CommonDrawerUtils {

	public ConvexHullDrawer(KMeansManager manager) {
		super(manager);
	}

	@Override
	public void drawOn(Graphics graphics) {
		for (Point centroid : manager.getCentroids()) {			
			final List<Point> ps = manager.getPointsOf(centroid);
			
			graphics.setColor(lightGray);
			drawRegions(graphics, ((Hull)build(ps, true)).getRegion());
			
			graphics.setColor(Color.red);
			graphics.fillOval((int)centroid.getX(), (int)centroid.getY(), CENTROID_DIAMETER, CENTROID_DIAMETER);			
		}		
	}
}
