package it.unimi.si.algo;

import it.unimi.si.algo.metric.KMeansMetric;
import it.unimi.si.persistence.Storage;

public class KMeansManagerWithStorage extends KMeansManager {
	private final Storage storage;

	public KMeansManagerWithStorage(KMeansMetric type) {
		super(type);
		
		storage = new Storage();
	}
	
	public void store(String actionCommand) {
		storage.save(actionCommand, points, centroids, association);
	}

	public void restore(String actionCommand) {
		storage.retrive(actionCommand, points, centroids, association);
	}	
}
