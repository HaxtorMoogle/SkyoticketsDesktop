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
import fr.skyost.tickets.desktop.utils.Utils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = -4083882619877335524L;
	private final Container pane = getContentPane();
	
	protected SettingsFrame() throws IOException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(Skyotickets.messages.getProperty("title-settings", "Settings"));
		setIconImage(ImageIO.read(Skyotickets.class.getResource("/fr/skyost/tickets/desktop/res/Settings.png")));
		setResizable(false);
		setSize(368, 208);
		pane.setLayout(null);
		
		final JLabel lblTimeOut = new JLabel(Skyotickets.messages.getProperty("lbl-timeout", "Time out :"));
		lblTimeOut.setBounds(10, 15, 227, 14);
		pane.add(lblTimeOut);
		
		final JTextField txtTimeOut = new JTextField();
		txtTimeOut.setBounds(249, 8, 107, 28);
		txtTimeOut.setColumns(10);
		txtTimeOut.setText(Skyotickets.properties.getProperty("time-out"));
		pane.add(txtTimeOut);
		
		final JLabel lblCheckForUpdates = new JLabel(Skyotickets.messages.getProperty("lbl-checkforupdates", "Check for updates :"));
		lblCheckForUpdates.setBounds(10, 42, 227, 14);
		pane.add(lblCheckForUpdates);
		
		final JTextField txtCheckForUpdates = new JTextField();
		txtCheckForUpdates.setBounds(249, 35, 107, 28);
		txtCheckForUpdates.setColumns(10);
		txtCheckForUpdates.setText(Skyotickets.properties.getProperty("check-for-updates"));
		pane.add(txtCheckForUpdates);
		
		final JLabel lblSettingsIconX = new JLabel(Skyotickets.messages.getProperty("lbl-settingsiconx", "Settings icon X :"));
		lblSettingsIconX.setBounds(10, 68, 227, 16);
		pane.add(lblSettingsIconX);
		
		final JTextField txtSettingsiconx = new JTextField();
		txtSettingsiconx.setBounds(249, 62, 107, 28);
		txtSettingsiconx.setColumns(10);
		txtSettingsiconx.setText(Skyotickets.properties.getProperty("settings-icon-x"));
		pane.add(txtSettingsiconx);
		
		final JLabel lblSettingsIconY = new JLabel(Skyotickets.messages.getProperty("lbl-settingsicony", "Settings icon Y :"));
		lblSettingsIconY.setBounds(10, 96, 227, 16);
		pane.add(lblSettingsIconY);
		
		final JTextField txtSettingsicony = new JTextField();
		txtSettingsicony.setBounds(249, 90, 107, 28);
		txtSettingsicony.setColumns(10);
		txtSettingsicony.setText(Skyotickets.properties.getProperty("settings-icon-y"));
		pane.add(txtSettingsicony);
		
		final JLabel lblDownloadLanguages = new JLabel(Skyotickets.messages.getProperty("lbl-downloadlanguages", "Download languages :"));
		lblDownloadLanguages.setBounds(10, 122, 227, 16);
		pane.add(lblDownloadLanguages);
		
		final JTextField txtDownloadLanguages = new JTextField();
		txtDownloadLanguages.setBounds(249, 116, 107, 28);
		txtDownloadLanguages.setColumns(10);
		txtDownloadLanguages.setText(Skyotickets.properties.getProperty("download-new-languages"));
		pane.add(txtDownloadLanguages);
		
		final JButton btnSave = new JButton(Skyotickets.messages.getProperty("btn-save", "Save"));
		btnSave.setBounds(0, 150, 362, 29);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				try {
					Skyotickets.properties.put("time-out", String.valueOf(Integer.parseInt(txtTimeOut.getText())));
					Skyotickets.properties.put("check-for-updates", String.valueOf(Boolean.valueOf(txtCheckForUpdates.getText())));
					final int x = Integer.parseInt(txtSettingsiconx.getText());
					final int y = Integer.parseInt(txtSettingsicony.getText());
					Skyotickets.properties.put("settings-icon-x", String.valueOf(x));
					Skyotickets.properties.put("settings-icon-y", String.valueOf(y));
					Skyotickets.properties.store(new FileOutputStream(new File(Utils.getApplicationDirectory(), "skyotickets.properties")), "Skyotickets Desktop");
					ConnectionFrame.setLocationlblSettings(x, y);
					Utils.hide(getJFrame());
					Utils.close(getJFrame());
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(getJFrame(), ex, Skyotickets.messages.getProperty("exception-error", "Error !"), JOptionPane.ERROR_MESSAGE);
				}
			}
		
		});
		pane.add(btnSave);
		
	}
	
	public JFrame getJFrame() {
		return this;
	}
}
