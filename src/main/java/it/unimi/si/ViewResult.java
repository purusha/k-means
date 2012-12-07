package it.unimi.si;

import it.unimi.si.util.ProgramConstant;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ViewResult extends JFrame implements ProgramConstant {
	private static final long serialVersionUID = 1L;

	public ViewResult(Properties properties) {
		setTitle("K-means Comparator Statistics");
		setSize(350, 515);
		setLocation(55, 55);
		setVisible(true);
		setResizable(false);
		
		final JPanel panel = new JPanel();
		final JTextArea textArea = new JTextArea();
		
		generateContent(properties, textArea);
		
		textArea.setEditable(false);
		textArea.setFont(new Font("Serif", Font.ITALIC, 13));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
        final JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(310, 470));
        areaScrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Result"),
                    BorderFactory.createEmptyBorder(15,15,15,15)
                ), 
                areaScrollPane.getBorder()
            )
        );
		
		panel.add(areaScrollPane);
		getContentPane().add(panel);		
	}

	private void generateContent(Properties properties, final JTextArea textArea) {
		float numEsecuzioni = 0;
		
		for(Object key : properties.keySet()) {
			if (key.equals(ESECUZIONI_KEY)) {
				textArea.append("### Esecuzioni effettuate ###\n");
				
				final String v = (String) properties.get(key);
				numEsecuzioni = Float.valueOf(v);
				
				textArea.append(v);
				textArea.append("\n");				
			}
		}
		
		textArea.append("\n### Mosse totali ###\n");
		for(Object key : properties.keySet()) {
			if (!key.equals(ESECUZIONI_KEY)) {
				textArea.append((String) key);
				textArea.append(" = ");
				textArea.append((String) properties.get(key));
				textArea.append("\n");
			}			
		}

		textArea.append("\n### Media mosse ###\n");
		for(Object key : properties.keySet()) {
			if (!key.equals(ESECUZIONI_KEY)) {
				textArea.append((String) key);
				textArea.append(" = ");
				
				final float i = Float.valueOf((String) properties.get(key));
				
				textArea.append(String.valueOf(i/numEsecuzioni));
				textArea.append("\n");				
			}
		}		
	}	
}
