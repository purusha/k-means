package it.unimi.si.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class HistoryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public HistoryPanel() {
		setBackground(Color.white);
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}
		
	protected JButton createHistoryButton(int i) {
		final JButton button = new JButton(generateLabl(i));
		
		button.setEnabled(false);
		button.setActionCommand(generateCommand(i));
		
		enableButtons();
		add(button);		
		
		return button;
	}	
	
	private String generateLabl(int i) {
		switch(i) {
			case 1:
				return "s " + String.valueOf(getNextIndex());
			case 2:
				return "c " + String.valueOf(getNextIndex());
			case 3:
				return "d " + String.valueOf(getNextIndex());
			default:
				throw new RuntimeException();
		}		
	}

	protected void disableButton(int indexComponent) {
		final Component[] components = getComponents();
		
		for(int i = 0; i < components.length; i++) {
			if (i + 1 == indexComponent) {
				components[i].setEnabled(false);
			} else {
				components[i].setEnabled(true);
			}
		}
	}

	private String generateCommand(int i) {
		String label = "[";
		
		label += (i == 1 ? 'S' : i == 2 ? 'C' : 'D');
		label += "-" + getNextIndex() + "]";
		
		return label;
	}

	private void enableButtons() {
		final Component[] components = getComponents();
		
		if (components.length > 0) {
			components[components.length - 1].setEnabled(true);			
		}
	}
	
	private int getNextIndex() {
		return getComponents().length + 1;
	}

	public boolean isActiveLastState() {
		final Component[] components = getComponents();
		final int size = components.length;
		
		return (size == 0 || !components[size - 1].isEnabled());
	}

}
