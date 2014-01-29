package fr.skyost.tickets.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import fr.skyost.tickets.desktop.frames.ConnectionFrame;
import fr.skyost.tickets.desktop.threads.Updater;

public class Skyotickets {
	
	public static String host;
	public static String port;
	public static final Properties properties = new Properties();
	
	public static final String VERSION = "0.3.1";
	
	public static void main(final String[] args) {
		try {
			final File appDir = getApplicationDirectory();
			if(!appDir.exists()) {
				appDir.mkdir();
			}
			final File propertiesFile = new File(appDir, "skyotickets.properties");
			if(propertiesFile.exists()) {
				properties.load(new FileInputStream(propertiesFile));
			}
			else {
				properties.put("propertiesVersion", "0.1");
				properties.put("host", "");
				properties.put("port", "");
				properties.put("password", "");
				properties.put("settingsIconX", "382");
				properties.put("settingsIconY", "0");
				properties.put("timeOut", "10000");
				properties.put("checkForUpdates", "true");
				propertiesFile.createNewFile();
				properties.store(new FileOutputStream(propertiesFile), "Skyotickets Desktop");
			}
			if(!setStyle("Nimbus")) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			final ConnectionFrame connectionFrame = new ConnectionFrame();
			connectionFrame.setLocationRelativeTo(null);
			connectionFrame.setVisible(true);
			if(Boolean.valueOf(properties.getProperty("checkForUpdates"))) {
				new Updater().start();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex, "Error !", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static final boolean setStyle(final String style) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		for(final LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
			if(lookAndFeelInfo.getName().equals(style)) {
				UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
				return true;
			}
		}
		return false;
	}
	
	public static final void hide(final JFrame jFrame) {
		jFrame.setVisible(false);
	}
	
	public static final void close(final JFrame jFrame) {
		jFrame.dispose();
	}
	
	public static File getApplicationDirectory() {
	    return new File(System.getProperty("user.home") + File.separator + "SkyoticketsDesktop" + File.separator);
	}
	
}
