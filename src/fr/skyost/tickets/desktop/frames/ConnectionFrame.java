package fr.skyost.tickets.desktop.frames;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Container;
import java.awt.Cursor;

import javax.swing.JLabel;

import fr.skyost.tickets.desktop.Skyotickets;
import fr.skyost.tickets.desktop.threads.RemoteControl;
import fr.skyost.tickets.desktop.utils.Utils;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ConnectionFrame extends JFrame {

	private static final long serialVersionUID = -4083882619877335524L;
	private final Container pane = getContentPane();
	private static final JLabel lblSettings = new JLabel(new ImageIcon(Skyotickets.class.getResource("/fr/skyost/tickets/desktop/res/Settings.png")));
	
	public ConnectionFrame() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Skyotickets Desktop v" + Skyotickets.VERSION);
		setIconImage(ImageIO.read(Skyotickets.class.getResource("/fr/skyost/tickets/desktop/res/Paper.png")));
		setResizable(false);
		setSize(480, 278);
		pane.setLayout(null);


		lblSettings.setBounds(Integer.parseInt(Skyotickets.properties.getProperty("settings-icon-x")), Integer.parseInt(Skyotickets.properties.getProperty("settings-icon-y")), 32, 32);
		lblSettings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSettings.addMouseListener(new MouseListener() {
	
			@Override
			public final void mouseClicked(final MouseEvent event) {
				try {
					new SettingsFrame().setVisible(true);
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(getJFrame(), ex, Skyotickets.messages.getProperty("exception-error", "Error !"), JOptionPane.ERROR_MESSAGE);
				}
			}
			
			@Override
			public final void mouseReleased(final MouseEvent event) {
				try {
					Skyotickets.properties.put("settings-icon-x", String.valueOf(lblSettings.getX()));
					Skyotickets.properties.put("settings-icon-y", String.valueOf(lblSettings.getY()));
					Skyotickets.properties.store(new FileOutputStream(new File(Utils.getApplicationDirectory(), "skyotickets.properties")), "Skyotickets Desktop");
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(getJFrame(), ex, Skyotickets.messages.getProperty("exception-error", "Error !"), JOptionPane.ERROR_MESSAGE);
				}
			}
			
			@Override
			public final void mousePressed(final MouseEvent event) {}
		
			@Override
			public final void mouseEntered(final MouseEvent event) {}
	
			@Override
			public final void mouseExited(final MouseEvent event) {}
				
		});
		lblSettings.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public final void mouseDragged(final MouseEvent event) {
				lblSettings.setLocation(lblSettings.getX() + event.getX(), lblSettings.getY() + event.getY());
			}

			@Override
			public final void mouseMoved(final MouseEvent event) {}
			
		});
		pane.add(lblSettings);
		
		final JLabel lblSkyotickets = new JLabel(new ImageIcon(Skyotickets.class.getResource("/fr/skyost/tickets/desktop/res/Skyotickets.png")));
		lblSkyotickets.setBounds(0, 6, 468, 90);
		pane.add(lblSkyotickets);
		
		final JLabel lblHost = new JLabel(Skyotickets.messages.getProperty("lbl-host", "Host :"));
		lblHost.setBounds(10, 117, 115, 14);
		pane.add(lblHost);
		
		final JTextField txtHost = new JTextField();
		txtHost.setBounds(137, 110, 331, 28);
		txtHost.setText(Skyotickets.properties.getProperty("host"));
		txtHost.setColumns(10);
		pane.add(txtHost);
		
		final JLabel lblPort = new JLabel(Skyotickets.messages.getProperty("lbl-port", "Port :"));
		lblPort.setBounds(10, 142, 115, 14);
		pane.add(lblPort);
		
		final JTextField txtPort = new JTextField();
		txtPort.setBounds(137, 135, 331, 28);
		txtPort.setText(Skyotickets.properties.getProperty("port"));
		txtPort.setColumns(10);
		pane.add(txtPort);
		
		final JLabel lblPass = new JLabel(Skyotickets.messages.getProperty("lbl-password", "Password :"));
		lblPass.setBounds(10, 166, 115, 14);
		pane.add(lblPass);
		
		final JPasswordField txtPass = new JPasswordField();
		txtPass.setBounds(137, 159, 331, 28);
		txtPass.setColumns(10);
		txtPass.setText(Skyotickets.properties.getProperty("password"));
		pane.add(txtPass);
		
		final JButton btnLogin = new JButton(Skyotickets.messages.getProperty("btn-login", "Login"));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLogin.setBounds(10, 203, 458, 34);
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				try {
					final String host = txtHost.getText();
					final String port = txtPort.getText();
					final String password = new String(txtPass.getPassword());
					if(host.length() == 0 || port.length() == 0 || password.length() == 0) {
						JOptionPane.showMessageDialog(getJFrame(), Skyotickets.messages.getProperty("msg-fields", "You must fill all fields !"), Skyotickets.messages.getProperty("exception-error", "Error !"), JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(Skyotickets.host == null) {
						Skyotickets.host = host;
						Skyotickets.port = port;
						Skyotickets.properties.put("host", host);
						Skyotickets.properties.put("port", port);
						Skyotickets.properties.put("password", password);
						Skyotickets.properties.store(new FileOutputStream(new File(Utils.getApplicationDirectory(), "skyotickets.properties")), "Skyotickets Desktop");
					}
					new RemoteControl(getJFrame(), host, port, "auth " + password).start();
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(getJFrame(), ex, Skyotickets.messages.getProperty("exception-error", "Error !"), JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		pane.add(btnLogin);
	}
	
	public JFrame getJFrame() {
		return this;
	}
	
	public static final void setLocationlblSettings(final int x, final int y) {
		lblSettings.setLocation(x, y);
	}

}
