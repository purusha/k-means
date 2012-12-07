package it.unimi.si.gui;

import it.unimi.si.PreferencesKey;
import it.unimi.si.PreferencesManager;
import it.unimi.si.algo.KMeansManagerWithStorage;
import it.unimi.si.algo.metric.KMeansMetric;
import it.unimi.si.util.ActionCommandSplitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int WINDOW_DIMENSION = 550;
	
	private final ButtonsPanel scrollPane;
	private final GraphPanel main;
	private final KMeansManagerWithStorage manager;
	
	public MainFrame() {
		manager = buildKMeansManager();
		
		setTitle("K-means");
		setSize(WINDOW_DIMENSION, WINDOW_DIMENSION);
		setLocation(0, 0);
	    setVisible(true);
	    setResizable(false);		
	    	    
	    addMouseListener(this);
	    
	    main = new GraphPanel(manager);
	    getContentPane().add(main, "Center");
		
		scrollPane = new ButtonsPanel(new HistoryPanel());		
		getContentPane().add(scrollPane, "South");	
	}

	private KMeansManagerWithStorage buildKMeansManager() {		
		final PreferencesManager preferencesManager = PreferencesManager.getInstance();
		final String metricClass = "it.unimi.si.algo.metric." + preferencesManager.get(PreferencesKey.METRIC);
    	
		Class<?> forName = null;
    	try {
    		forName = Class.forName(metricClass);
		} catch (ClassNotFoundException e) {}
		
		System.out.println("Metric: " + forName);
		KMeansMetric metric = null;
		
		try {
			metric = (KMeansMetric) forName.newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {}
		
		return new KMeansManagerWithStorage(metric);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (scrollPane.isActiveLastState()) {
			final JButton button = scrollPane.createHistoryButton(e.getButton());
			button.addActionListener(this);
			
			scrollPane.validate();		
			scrollPane.getHorizontalScrollBar().setValue(
				scrollPane.getHorizontalScrollBar().getMaximum()); //scrolling for view the last added button
			
			main.mouseClicked(e);
			
			manager.store(button.getActionCommand());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		final String actionCommand = event.getActionCommand();
		
		scrollPane.disableButton(new ActionCommandSplitter(actionCommand).getNumber());
		manager.restore(actionCommand);		
		main.repaint();
	}	
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}