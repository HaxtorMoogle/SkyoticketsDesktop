package fr.skyost.tickets.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import fr.skyost.tickets.desktop.frames.ConnectionFrame;
import fr.skyost.tickets.desktop.threads.Downloader;
import fr.skyost.tickets.desktop.threads.Updater;
import fr.skyost.tickets.desktop.utils.Utils;

public class Skyotickets {
	
	public static String host;
	public static String port;
	public static final Properties properties = new Properties();
	public static final Properties messages = new Properties();
	
	public static final String VERSION = "0.4";
	public static final String CONFIG_VERSION = "0.2";
	public static final String MESSAGES_VERSION = "0.1";
	public static final String LANGUAGE = System.getProperty("user.language");
	
	public static void main(final String[] args) {
		try {
			if(!Utils.setLookAndFeelsFromInstalled("Nimbus")) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			final File appDir = Utils.getApplicationDirectory();
			if(!appDir.exists()) {
				appDir.mkdir();
			}
			final File propertiesFile = new File(appDir, "skyotickets.properties");
			if(propertiesFile.exists()) {
				properties.load(new FileInputStream(propertiesFile));
				if(Utils.compareVersions(properties.getProperty("properties-version"), CONFIG_VERSION)) {
					properties.clear();
					properties.putAll(Utils.buildDefaultConfig());
					propertiesFile.delete();
					propertiesFile.createNewFile();
					properties.store(new FileOutputStream(propertiesFile), "Skyotickets Desktop");
				}
			}
			else {
				properties.clear();
				properties.putAll(Utils.buildDefaultConfig());
				propertiesFile.createNewFile();
				properties.store(new FileOutputStream(propertiesFile), "Skyotickets Desktop");
			}
			final File languageFile = new File(appDir, LANGUAGE + ".properties");
			if(languageFile.exists() && !LANGUAGE.startsWith("en")) {
				messages.load(new FileInputStream(languageFile));
				if(Boolean.valueOf(properties.getProperty("download-new-languages"))) {
					if(Utils.compareVersions(messages.getProperty("properties-version"), MESSAGES_VERSION)) {
						new Downloader("http://www.skyost.eu/skyotickets-languages/" + LANGUAGE + ".properties", languageFile).start();
					}
				}
			}
			else {
				if(Boolean.valueOf(properties.getProperty("download-new-languages"))) {
					new Downloader("http://www.skyost.eu/skyotickets-languages/" + LANGUAGE + ".properties", languageFile).start();
				}
			}
			final ConnectionFrame connectionFrame = new ConnectionFrame();
			connectionFrame.setLocationRelativeTo(null);
			connectionFrame.setVisible(true);
			if(Boolean.valueOf(properties.getProperty("check-for-updates"))) {
				new Updater().start();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex, messages.getProperty("exception-error", "Error !"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
