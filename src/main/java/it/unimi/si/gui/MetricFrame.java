package it.unimi.si.gui;

import it.unimi.si.algo.metric.ChessboardMetric;
import it.unimi.si.algo.metric.EuclidesMetric;
import it.unimi.si.algo.metric.ManhattansMetric;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MetricFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private final Random random = new Random(System.currentTimeMillis());
	private final List<SingleMetricManager> metrics = new ArrayList<SingleMetricManager>();

	public MetricFrame() {
		buildMetrics();
		
		setTitle("K-means Comparator");
		setSize(1050, 515);
		setLocation(50, 50);
		setVisible(true);
		setResizable(false);
		setLayout(new FlowLayout());
		
		final Container pane = getContentPane();
		
		pane.add(metrics.get(0).getTitlePanel());
		pane.add(metrics.get(1).getTitlePanel());
		pane.add(metrics.get(2).getTitlePanel());

		pane.add(metrics.get(0).getGraphPanel());
		pane.add(metrics.get(1).getGraphPanel());
		pane.add(metrics.get(2).getGraphPanel());
		
		pane.add(metrics.get(0).getResultPanel());
		pane.add(metrics.get(1).getResultPanel());
		pane.add(metrics.get(2).getResultPanel());		
		
		final JButton fire = new JButton("run one step of k-means algorythms");
		fire.setPreferredSize(new Dimension(1020, 30));
		
		fire.addActionListener(metrics.get(0));
		fire.addActionListener(metrics.get(1));
		fire.addActionListener(metrics.get(2));
		
		pane.add(fire);		
	}

	private void buildMetrics() {
		final List<Point> points = generatePoints(50);
		final List<Point> centroids = generatePoints(6);
		
		metrics.add(new SingleMetricManager(new EuclidesMetric(), points, centroids));
		metrics.add(new SingleMetricManager(new ManhattansMetric(), points, centroids));
		metrics.add(new SingleMetricManager(new ChessboardMetric(), points, centroids));	
	}

	private List<Point> generatePoints(int n) {
		final List<Point> ret = new ArrayList<Point>();
		random.setSeed(System.currentTimeMillis());
		
		for(int i = 0; i < n; i++) {
			final int x = random.nextInt(SingleMetricManager.PANEL_WIDTH - 2);
			final int y = random.nextInt(SingleMetricManager.PANEL_HEIGHT - 2);
			
			ret.add(new Point(x, y));
		}
		
		return ret;
	}
}
