package fr.skyost.tickets.desktop.frames;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Container;
import javax.swing.JLabel;

import fr.skyost.tickets.desktop.Skyotickets;
import fr.skyost.tickets.desktop.threads.RemoteControl;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CommandFrame extends JFrame {

	private static final long serialVersionUID = -4083882619877335524L;
	private final Container pane = getContentPane();
	private static final JTextField txtCommand = new JTextField();
	
	public CommandFrame() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(ImageIO.read(Skyotickets.class.getResource("/fr/skyost/tickets/desktop/res/Paper.png")));
		setResizable(false);
		setSize(480, 210);
		pane.setLayout(null);
		
		final JLabel lblSkyotickets = new JLabel(new ImageIcon(Skyotickets.class.getResource("/fr/skyost/tickets/desktop/res/Skyotickets.png")));
		lblSkyotickets.setBounds(0, 6, 468, 90);
		pane.add(lblSkyotickets);
		
		final JLabel lblCommand = new JLabel(Skyotickets.messages.getProperty("lbl-command", "Command :"));
		lblCommand.setBounds(10, 112, 131, 14);
		pane.add(lblCommand);
		
		txtCommand.setFont(new Font("SansSerif", Font.PLAIN, 12));
		txtCommand.setBounds(153, 105, 315, 28);
		txtCommand.setColumns(10);
		pane.add(txtCommand);
		
		final JButton btnSend = new JButton(Skyotickets.messages.getProperty("btn-send", "Send !"));
		btnSend.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSend.setBounds(10, 137, 458, 34);
		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				try {
					new RemoteControl(getJFrame(), Skyotickets.host, Skyotickets.port, txtCommand.getText()).start();
				}
				catch(IOException ex) {
					JOptionPane.showMessageDialog(getJFrame(), ex, Skyotickets.messages.getProperty("exception-error", "Error !"), JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		pane.add(btnSend);
	}
	
	public JFrame getJFrame() {
		return this;
	}
	
	public static void resetTxtCommand() {
		txtCommand.setText(null);
	}
	
}
