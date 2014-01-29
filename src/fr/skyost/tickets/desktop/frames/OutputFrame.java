package fr.skyost.tickets.desktop.frames;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

public class OutputFrame extends JFrame {

	private static final long serialVersionUID = -4083882619877335524L;
	private final String lineSeparator = System.getProperty("line.separator");
	private JTextArea txtOutput;
	
	private static OutputFrame instance;
	
	public OutputFrame() {
		instance = this;
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setSize(320, 240);
		txtOutput = new JTextArea();
		txtOutput.setForeground(Color.WHITE);
		txtOutput.setBackground(Color.BLACK);
		txtOutput.setText("Output :");
		txtOutput.setEditable(false);
		txtOutput.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		getContentPane().add(new JScrollPane(txtOutput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
	}
	
	public void addText(final String text) {
		txtOutput.setText(txtOutput.getText() + lineSeparator + lineSeparator + text.replaceAll("/n/", lineSeparator));
	}
	
	public static final OutputFrame getInstance() {
		return instance;
	}
	
}
