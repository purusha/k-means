package it.unimi.si.gui;

import it.unimi.si.PreferencesKey;
import it.unimi.si.PreferencesManager;
import it.unimi.si.algo.KMeansManager;
import it.unimi.si.gui.drawer.CentroidDrawer;
import it.unimi.si.util.DrawConstant;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import javax.swing.JPanel;

public class GraphPanel extends JPanel implements DrawConstant {
	private static final long serialVersionUID = 1L;
	private final Random random = new Random(System.currentTimeMillis());
	
	private final int BORDER = 10;
	private final int NUMBER_OF_POINTS = 20;
	
	private final PreferencesManager preferencesManager;
	private KMeansManager manager;
	private CentroidDrawer drawer;
	
	public GraphPanel(KMeansManager m) {
		manager = m;
		preferencesManager = PreferencesManager.getInstance();
		
		drawer = buildDrawer();
	}
		
    private CentroidDrawer buildDrawer() {
    	final String drawerClass = "it.unimi.si.gui.drawer." + preferencesManager.get(PreferencesKey.DRAWER);
    	
    	Class<?> forName = null;
    	try {
    		forName = Class.forName(drawerClass);
    	} catch (ClassNotFoundException e) {}
    	
    	System.out.println("Drawer: " + forName);
    	
    	CentroidDrawer drawer = null;
    	
    	try {
    		Constructor<?> declaredConstructor = forName.getDeclaredConstructor(KMeansManager.class);
    		drawer = (CentroidDrawer) declaredConstructor.newInstance(manager);
    	} catch (SecurityException e) {
    	} catch (NoSuchMethodException e) {
    	} catch (IllegalArgumentException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {}
		
		return drawer;
	}

	public void paintComponent(Graphics graphics){
    	super.paintComponent(graphics);
    	setBackground(Color.black);   
    	
    	//draw centroid in someway
    	drawer.drawOn(graphics);
    	
    	//draw points
    	graphics.setColor(Color.yellow);    	
    	for (Point point : manager.getPoints()) {
    		graphics.drawOval(point.x, point.y, POINT_DIAMETER, POINT_DIAMETER);
    	}    	
    }

	public void mouseClicked(MouseEvent event) {
		switch (event.getButton()) {
			case 1:				
				generateCentroid(event.getPoint());	
				break;
				
			case 2:
				manager.pointClassification();
				manager.centroidUpdate();
				break;
				
			case 3:
				generatePoints();
				break;
		}
		
		repaint();
	}

//	private void printData() {
//		System.out.println("--------------------------------------------------");
//		List<Point> points = manager.getPoints();
//		System.out.println("number of points = " + points.size());
//				
//		List<Point> centroids = manager.getCentroids();
//		System.out.println("number of centroids = " + centroids.size());
//		
//		for (Point centroid : centroids) {
//			System.out.println("##############");
//			System.out.println("current centroid = " + centroid.getClass().getName() + '@' + Integer.toHexString(centroid.hashCode()));
//			
//			List<Point> pointsOf = manager.getPointsOf(centroid);		
//			for (Point point : pointsOf) {
//				System.out.println("current point = " + point);
//			}
//		}
//		
//	}

	private void generateCentroid(Point point) {	
		point.setLocation(point.getX(), point.getY() - 55); //30 is TITLE BAR + 25 is MENU BAR		
		manager.addCentroid(point);
	}

	private void generatePoints() {
    	final Dimension dimensioniPanel = getSize();
    	final double maxW = dimensioniPanel.getWidth() - (2 * BORDER);
    	final double maxH = dimensioniPanel.getHeight() - (2 * BORDER);

    	for (int i = 0; i < NUMBER_OF_POINTS; i++){
    		int x = (int)(random.nextDouble() * maxW) + BORDER;
    		int y = (int)(random.nextDouble() * maxH) + BORDER;
    		
    		manager.addPoint(new Point(x, y));
    	}		
	}
}
