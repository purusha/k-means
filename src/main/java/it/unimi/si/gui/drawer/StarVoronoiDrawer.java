package it.unimi.si.gui.drawer;

import it.unimi.si.algo.KMeansManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import megamu.mesh.Voronoi;

public class StarVoronoiDrawer extends CommonDrawerUtils {

	public StarVoronoiDrawer(KMeansManager manager) {
		super(manager);
	}

	@Override
	public void drawOn(Graphics graphics) {		
		final List<Point> ps = manager.getCentroids();
		final Voronoi voronoi = ((Voronoi)build(ps, false));
		
		//voronoi drawer		
		graphics.setColor(lightGray);
		drawRegions(graphics, voronoi.getRegions());
		
		//central drawer
		for (Point centroid : ps) {			
			graphics.setColor(lightGray);
			final List<Point> ps1 = manager.getPointsOf(centroid);
			for (Point p : ps1) {
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
