package it.unimi.si.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ButtonsPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;
	private HistoryPanel historyPanel;
	
	public ButtonsPanel(HistoryPanel historyPanel) {
		super(historyPanel);		
		super.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		super.setPreferredSize(new Dimension(Integer.MAX_VALUE, 55));
		
		this.historyPanel = historyPanel;
	}

	protected JButton createHistoryButton(int i) {
		return historyPanel.createHistoryButton(i);
	}
	
	protected void disableButton(int i) {
		historyPanel.disableButton(i);
	}

	public boolean isActiveLastState() {
		return historyPanel.isActiveLastState();
	}
}
