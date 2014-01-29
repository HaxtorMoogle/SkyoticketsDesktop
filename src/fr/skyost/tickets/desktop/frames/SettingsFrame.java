package fr.skyost.tickets.desktop.frames;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import fr.skyost.tickets.desktop.Skyotickets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = -4083882619877335524L;
	private final Container pane = getContentPane();
	
	protected SettingsFrame() throws IOException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Settings");
		setIconImage(ImageIO.read(Skyotickets.class.getResource("/fr/skyost/tickets/desktop/res/Settings.png")));
		setResizable(false);
		setSize(260, 208);
		pane.setLayout(null);
		
		final JLabel lblTimeOut = new JLabel("Time out :");
		lblTimeOut.setBounds(10, 15, 54, 14);
		pane.add(lblTimeOut);
		
		final JTextField txtTimeOut = new JTextField();
		txtTimeOut.setBounds(127, 8, 117, 28);
		txtTimeOut.setColumns(10);
		txtTimeOut.setText(Skyotickets.properties.getProperty("timeOut"));
		pane.add(txtTimeOut);
		
		final JLabel lblCheckForUpdates = new JLabel("Check for updates :");
		lblCheckForUpdates.setBounds(10, 42, 107, 14);
		pane.add(lblCheckForUpdates);
		
		final JTextField txtCheckForUpdates = new JTextField();
		txtCheckForUpdates.setBounds(127, 34, 117, 28);
		txtCheckForUpdates.setColumns(10);
		txtCheckForUpdates.setText(Skyotickets.properties.getProperty("checkForUpdates"));
		pane.add(txtCheckForUpdates);
		
		final JLabel lblSettingsIconX = new JLabel("Settings icon X :");
		lblSettingsIconX.setBounds(10, 68, 87, 16);
		pane.add(lblSettingsIconX);
		
		final JTextField txtSettingsiconx = new JTextField();
		txtSettingsiconx.setBounds(127, 62, 117, 28);
		txtSettingsiconx.setColumns(10);
		txtSettingsiconx.setText(Skyotickets.properties.getProperty("settingsIconX"));
		pane.add(txtSettingsiconx);
		
		final JLabel lblSettingsIconY = new JLabel("Settings icon Y :");
		lblSettingsIconY.setBounds(10, 96, 87, 16);
		pane.add(lblSettingsIconY);
		
		final JTextField txtSettingsicony = new JTextField();
		txtSettingsicony.setBounds(127, 90, 117, 28);
		txtSettingsicony.setColumns(10);
		txtSettingsicony.setText(Skyotickets.properties.getProperty("settingsIconY"));
		pane.add(txtSettingsicony);
		
		final JButton btnSave = new JButton("Save");
		btnSave.setBounds(0, 150, 254, 29);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				try {
					Skyotickets.properties.put("timeOut", String.valueOf(Integer.parseInt(txtTimeOut.getText())));
					Skyotickets.properties.put("checkForUpdates", String.valueOf(Boolean.valueOf(txtCheckForUpdates.getText())));
					final int x = Integer.parseInt(txtSettingsiconx.getText());
					final int y = Integer.parseInt(txtSettingsicony.getText());
					Skyotickets.properties.put("settingsIconX", String.valueOf(x));
					Skyotickets.properties.put("settingsIconY", String.valueOf(y));
					Skyotickets.properties.store(new FileOutputStream(new File(Skyotickets.getApplicationDirectory(), "skyotickets.properties")), "Skyotickets Desktop");
					ConnectionFrame.setLocationlblSettings(x, y);
					Skyotickets.hide(getJFrame());
					Skyotickets.close(getJFrame());
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(getJFrame(), ex, "Error !", JOptionPane.ERROR_MESSAGE);
				}
			}
		
		});
		pane.add(btnSave);
		
	}
	
	public JFrame getJFrame() {
		return this;
	}
}
