package it.unimi.si;

import it.unimi.si.gui.MainFrame;
import it.unimi.si.gui.MainFrameWindowListener;
import it.unimi.si.gui.MetricFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class ApplicationManager implements ActionListener {
	private final PreferencesManager preferencesManager;
	
	private MainFrame mainFrame;
	private MetricFrame metricFrame;
	private JMenuItem compareMetric;
	private JMenuItem exit;

	public ApplicationManager() {
		preferencesManager = PreferencesManager.getInstance();
		
		createMainFrame();
	}

	private void createMainFrame() {
		mainFrame = new MainFrame();		
		mainFrame.setJMenuBar(buildMenu());
		mainFrame.addWindowListener(new MainFrameWindowListener());
	}
	
	private JMenuBar buildMenu() {
		final JMenuBar menuBar = new JMenuBar();
	    
		final JMenu application = new JMenu("Application");
	    	final JMenu configure = new JMenu("Configure");
	    		final JMenu drawer = new JMenu("Type of drawer");
	    		final JMenu history = new JMenu("History persistence");
	    		final JMenu metric = new JMenu("Type of metric");
	    	compareMetric = new JMenuItem("Compare metrics");
	    	exit = new JMenuItem("Exit");
	    	
	    compareMetric.addActionListener(this);
	    exit.addActionListener(this);
	    
	    application.add(configure);
	    application.add(compareMetric);
	    application.addSeparator();
	    application.add(exit);
	    
	    menuBar.add(application);
	    
	    configure.add(drawer);
	    configure.add(history);
	    configure.add(metric);
	    
	    createChildMenu(drawer, PreferencesKey.DRAWER.getData());
	    createChildMenu(history, PreferencesKey.PERSISTENCE.getData());
	    createChildMenu(metric, PreferencesKey.METRIC.getData());
	    
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final WindowListener[] windowListeners = mainFrame.getWindowListeners();
		final Object source = e.getSource();
		
		if (source == compareMetric) {
			metricFrame = new MetricFrame();
			
			metricFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					
					final StatisticManager statistic = new StatisticManager();
					new ViewResult(statistic.getProperties());
					
				}
			});
		} else if (source == exit) {
			if (metricFrame != null) {
				metricFrame.dispose();
			}
			
			for(WindowListener wl : windowListeners) {
				if (wl.getClass().getSimpleName().equals(MainFrameWindowListener.class.getSimpleName())) {
					((MainFrameWindowListener)wl).windowClosing(null); //todo ci vorrebbe un'alternativa !!?
				}
			}
		} else {
			final String actionCommand = e.getActionCommand();
			
			preferencesManager.set(PreferencesKey.getPreferencesKeyBy(actionCommand), actionCommand);
			
			if (metricFrame != null) {
				metricFrame.dispose();
			}			
			
			for(WindowListener wl : windowListeners) {
				if (wl.getClass().getSimpleName().equals(MainFrameWindowListener.class.getSimpleName())) {
					((MainFrameWindowListener)wl).cleanHistory(); //todo ci vorrebbe un'alternativa !!?
				}
			}
			
			mainFrame.dispose();
			
			createMainFrame();
		}
	}	
	
	private void createChildMenu(final JMenu fatherMenu, final List<String> list) {
		final ButtonGroup group = new ButtonGroup();

	    for (String key : list) {
			final JRadioButtonMenuItem jRadioButtonMenuItem = new JRadioButtonMenuItem(key);
	    	jRadioButtonMenuItem.addActionListener(this);
	    	jRadioButtonMenuItem.setSelected(preferencesManager.isActive(key));
	    	jRadioButtonMenuItem.setActionCommand(key);
	    	
	    	group.add(jRadioButtonMenuItem);
	    	fatherMenu.add(jRadioButtonMenuItem);			
		}
	}		
}
