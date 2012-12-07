package it.unimi.si.gui;

import it.unimi.si.PreferencesKey;
import it.unimi.si.PreferencesManager;
import it.unimi.si.algo.KMeansManager;
import it.unimi.si.algo.metric.KMeansMetric;
import it.unimi.si.gui.drawer.CentroidDrawer;
import it.unimi.si.util.DrawConstant;
import it.unimi.si.util.Score;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class SingleMetricManager implements ActionListener {
	
	final static int PANEL_WIDTH = 340;
	final static int PANEL_HEIGHT = 400;
	
	final private JPanel graph, result, title;
	final private KMeansManager manager;
	final Score score;
	
	public SingleMetricManager(KMeansMetric type, List<Point> points, List<Point> centroids) {
		score = new Score(type.getName());
		manager = new KMeansManager(type);

		for (Point p : copy(points)) {
			manager.addPoint(p);
		}
		
		for (Point c : copy(centroids)) {			
			manager.addCentroid(c);
		}
				
		title = new JPanel();
		title.add(new Label(type.getName()));
		title.setPreferredSize(new Dimension(PANEL_WIDTH, 25));		
		
		graph = new MetricPanel(manager);
		graph.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		result = new JPanel();
		result.setPreferredSize(new Dimension(PANEL_WIDTH, 5));
		result.setBackground(Color.RED);		
	}

	public JPanel getGraphPanel() {
		return graph;
	}
	
	public JPanel getTitlePanel() {
		return title;
	}

	public JPanel getResultPanel() {
		return result;
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		score.setProperty(String.valueOf(System.currentTimeMillis()), ".");
		
		final List<Point> pointsBefore = copy(manager.getPoints());
		final List<Point> centroidsBefore = copy(manager.getCentroids());
		
		//one step of KMeans Algo
		manager.pointClassification();
		manager.centroidUpdate();
		
		if (!thereAreChanges(manager.getPoints(), pointsBefore) && !thereAreChanges(manager.getCentroids(), centroidsBefore)) {
			result.setBackground(Color.GREEN);
			
			score.endOfPlay();
		}
		
		score.storeAll();
		graph.repaint();
	}	
	
	private boolean thereAreChanges(List<Point> data, List<Point> dataBefore) {
		if (data.size() != dataBefore.size()) {
			return true;
		}
		
		for (Point d : data) {
			if (!dataBefore.contains(d)) {
				return true;
			}
		}
		
		return false;
	}

	private List<Point> copy(List<Point> src) {
		final List<Point> dest = new ArrayList<Point>();
		
		if (src != null) {
			for(Point point : src) {
				dest.add(new Point(point.x, point.y));
			}			
		}
		
		return dest; 
	}	
	
	/*
	 * -----------------------------------------------------------------------------------------------
	 */
	
	private class MetricPanel extends JPanel implements DrawConstant {
		private static final long serialVersionUID = 1L;
		
		private final PreferencesManager preferencesManager;
		private KMeansManager manager;
		private CentroidDrawer drawer;	
		
		public MetricPanel(KMeansManager m) {
			manager = m;
			preferencesManager = PreferencesManager.getInstance();
			
			drawer = buildDrawer();    	
		}
		
		@Override
		public void paintComponent(Graphics graphics){
	    	super.paintComponent(graphics);
	    	setBackground(Color.BLACK);
	    	
	    	drawer.drawOn(graphics);
	    	
	    	graphics.setColor(Color.yellow);    	
	    	for (Point point : manager.getPoints()) {
	    		graphics.drawOval(point.x, point.y, POINT_DIAMETER, POINT_DIAMETER);
	    	}    	
	    }
		
		private CentroidDrawer buildDrawer() {
	    	final String drawerClass = "it.unimi.si.gui.drawer." + preferencesManager.get(PreferencesKey.DRAWER);
	    	
	    	Class<?> forName = null;
	    	try {
	    		forName = Class.forName(drawerClass);
	    	} catch (ClassNotFoundException e) {}
	    	
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
	}
}
