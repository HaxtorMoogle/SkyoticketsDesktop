package fr.skyost.tickets.desktop.frames;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.skyost.tickets.desktop.Skyotickets;

import java.awt.BorderLayout;

public class OutputFrame extends JFrame {

	private static final long serialVersionUID = -4083882619877335524L;
	private final Container pane = getContentPane();
	private final String lineSeparator = System.lineSeparator();
	private JTextArea txtOutput;
	
	private static OutputFrame instance;
	
	public OutputFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		instance = this;
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setSize(320, 240);
		txtOutput = new JTextArea();
		txtOutput.setForeground(Color.WHITE);
		txtOutput.setBackground(Color.BLACK);
		txtOutput.setText(Skyotickets.messages.getProperty("txt-output", "Output :"));
		txtOutput.setEditable(false);
		txtOutput.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		final JScrollPane scrollPane = new JScrollPane(txtOutput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.add(scrollPane);
		
		final JButton btnClearOutput = new JButton(Skyotickets.messages.getProperty("btn-output", "Clear output"));
		btnClearOutput.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				txtOutput.setText(null);
			}
			
		});
		pane.add(btnClearOutput, BorderLayout.SOUTH);
	}
	
	public void addText(final String text) {
		txtOutput.setText(txtOutput.getText() + lineSeparator + lineSeparator + text.replaceAll("/n/", lineSeparator));
	}
	
	public static final OutputFrame getInstance() {
		return instance;
	}
	
}
