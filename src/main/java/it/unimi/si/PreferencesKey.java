package it.unimi.si;

import it.unimi.si.algo.metric.ChessboardMetric;
import it.unimi.si.algo.metric.EuclidesMetric;
import it.unimi.si.algo.metric.ManhattansMetric;
import it.unimi.si.gui.drawer.ConvexHullDrawer;
import it.unimi.si.gui.drawer.PerimetricDrawer;
import it.unimi.si.gui.drawer.StarDrawer;
import it.unimi.si.gui.drawer.StarVoronoiDrawer;
import it.unimi.si.gui.drawer.VoronoiDrawer;
import it.unimi.si.persistence.PersistenceType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PreferencesKey implements StaticData {
	DRAWER(ConvexHullDrawer.class.getSimpleName(), drawers), 		
	METRIC(ManhattansMetric.class.getSimpleName(), metrics), 		
	PERSISTENCE(PersistenceType.MEMORY.name(), persistences);
	
	public static PreferencesKey getPreferencesKeyBy(String value) {
		for(PreferencesKey pk : PreferencesKey.values()) {
			if(pk.getData().contains(value)){
				return pk;
			}
		}
		
		return null;
	}
			
	private final String d;
	private final List<String> l;
	
	private PreferencesKey(String d, List<String> l) {
		this.d = d;
		this.l = l;
	}

	public String getDefaultValue() {
		return d;
	} 
	
	public List<String> getData() {
		return Collections.unmodifiableList(l);
	}	
}

/*
 * -----------------------------------------------------------------------------------------------
 */

interface StaticData {
	static final List<String> drawers = Arrays.asList(
		StarDrawer.class.getSimpleName(), ConvexHullDrawer.class.getSimpleName(),
		PerimetricDrawer.class.getSimpleName(), StarVoronoiDrawer.class.getSimpleName(),
		VoronoiDrawer.class.getSimpleName()
	);
	
	static final List<String> persistences = Arrays.asList(
		PersistenceType.MEMORY.name(), PersistenceType.FILE.name()
	);
	
	static final List<String> metrics = Arrays.asList(
		EuclidesMetric.class.getSimpleName(), ManhattansMetric.class.getSimpleName(), ChessboardMetric.class.getSimpleName()
	);
}
