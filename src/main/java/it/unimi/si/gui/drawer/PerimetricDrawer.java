package it.unimi.si.gui.drawer;

import it.unimi.si.algo.KMeansManager;
import it.unimi.si.util.DrawConstant;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public class PerimetricDrawer extends CentroidDrawer implements DrawConstant {
	
	public PerimetricDrawer(KMeansManager manager) {
		super(manager);
	}	
	
	@Override
	public void drawOn(Graphics graphics) {
    	for (Point centroid : manager.getCentroids()) {
    		graphics.setColor(lightGray);
			createPolygonFrom(graphics, centroid, manager.getPointsOf(centroid));
			
			graphics.setColor(Color.red);
			graphics.fillOval((int)centroid.getX(), (int)centroid.getY(), CENTROID_DIAMETER, CENTROID_DIAMETER);			
    	}		
	}

	private void createPolygonFrom(Graphics graphics, Point centroid, final List<Point> pointsOf) {
		final List<Polygon> polys = new ArrayList<Polygon>();
		Polygon p = new Polygon();
		
		for (int i = 0; i < pointsOf.size(); i++) {
			final Point point = pointsOf.get(i);
			
			/*
			 * IDEA
			 * 
			 * per migliorare le performance si potrebbe ricercare il point corrent 
			 * in uno dei poligoni creati ... se è contenuto, non si esegue l'operazione
			 */
			
			switch (i % 2) {
				case 0:
					p.addPoint(point.x, point.y);
					
					//ultimo elemento
					if (i == pointsOf.size() - 1 && i > 0) {
						final Point prevoius = pointsOf.get(i - 1);
						p.addPoint(prevoius.x, prevoius.y);
						p.addPoint(centroid.x, centroid.y);
						polys.add(p);
					}
					
					break;
	
				case 1:
					p.addPoint(point.x, point.y);
					p.addPoint(centroid.x, centroid.y);
					polys.add(p);
					p = new Polygon();
					
					break;
			}
		}
		
		drawPolygons(graphics, polys);				
	}

	private void drawPolygons(Graphics graphics, final List<Polygon> polys) {
		for (Polygon polygon : polys) {
			graphics.fillPolygon(polygon);				
		}
	}
}

