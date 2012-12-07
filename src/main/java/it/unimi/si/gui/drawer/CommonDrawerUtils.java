package it.unimi.si.gui.drawer;

import it.unimi.si.algo.KMeansManager;
import it.unimi.si.util.DrawConstant;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import megamu.mesh.Hull;
import megamu.mesh.MPolygon;
import megamu.mesh.Voronoi;

public abstract class CommonDrawerUtils extends CentroidDrawer implements DrawConstant {

	public CommonDrawerUtils(KMeansManager manager) {
		super(manager);
	}
	
	public abstract void drawOn(Graphics graphics);

	protected void drawRegions(Graphics graphics, MPolygon... regions) {
		for(MPolygon polygon : regions) {
			final float[][] coords = polygon.getCoords();
			
			int[] x = subArray(coords, 0);
			int[] y = subArray(coords, 1);
			
			graphics.drawPolygon(x, y, x.length);		
		}		
	}	
	
	protected Object build(List<Point> ps, boolean hullVSvoronoi) { //add Enum or Class ... remove boolean !!?
		float[][] points = new float[ps.size()][2];
		
		for (int i = 0; i < ps.size(); i++) {
			final Point current = ps.get(i);
			
			points[i][0] = current.x;
			points[i][1] = current.y;
		}
		
		return hullVSvoronoi ? new Hull(points) : new Voronoi(points);
	}	
	
	private int[] subArray(float[][] coords, int i) {
		final int[] ret = new int[coords.length];
		
		for (int j = 0; j < coords.length; j++) {
			ret[j] = (int) coords[j][i];
		}
		
		return ret;
	}	

}
