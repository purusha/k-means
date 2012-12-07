package it.unimi.si.gui.drawer;

import it.unimi.si.algo.KMeansManager;

import java.awt.Graphics;

public abstract class CentroidDrawer {
	
	final KMeansManager manager;
	
	public CentroidDrawer(KMeansManager manager) {
		this.manager = manager;		
	}
	
	public abstract void drawOn(Graphics graphics);
	
}
