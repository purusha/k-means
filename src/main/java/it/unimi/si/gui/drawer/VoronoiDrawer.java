package it.unimi.si.gui.drawer;

import it.unimi.si.algo.KMeansManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import megamu.mesh.Voronoi;

public class VoronoiDrawer extends CommonDrawerUtils {

	public VoronoiDrawer(KMeansManager manager) {
		super(manager);
	}

	@Override
	public void drawOn(Graphics graphics) {		
		final List<Point> ps = manager.getCentroids();
		final Voronoi voronoi = ((Voronoi)build(ps, false));
		
		graphics.setColor(lightGray);
		drawRegions(graphics, voronoi.getRegions());
		
		graphics.setColor(Color.red);
		for (Point centroid : manager.getCentroids()) {
			graphics.fillOval((int)centroid.getX(), (int)centroid.getY(), CENTROID_DIAMETER, CENTROID_DIAMETER);			
		}		
	}
}
