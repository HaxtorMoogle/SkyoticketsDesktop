package fr.skyost.tickets.desktop.utils;

import java.io.File;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

public class Utils {
	
	public static final Properties buildDefaultConfig() {
		final Properties defaultProperties = new Properties();
		defaultProperties.put("properties-version", "0.2");
		defaultProperties.put("host", "");
		defaultProperties.put("port", "");
		defaultProperties.put("password", "");
		defaultProperties.put("settings-icon-x", "438");
		defaultProperties.put("settings-icon-y", "0");
		defaultProperties.put("time-out", "10000");
		defaultProperties.put("check-for-updates", "true");
		defaultProperties.put("download-new-languages", "true");
		return defaultProperties;
	}
	
	public static final boolean setLookAndFeelsFromInstalled(final String lookAndFeels) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		for(final LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
			if(lookAndFeelInfo.getName().equals(lookAndFeels)) {
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
	
	public static final File getSkyostDirectory() {
		return new File(System.getProperty("user.home") + File.separator + ".skyost" + File.separator);
	}
	
	public static File getApplicationDirectory() {
		final File skyostDir = getSkyostDirectory();
		if(!skyostDir.exists()) {
			skyostDir.mkdir();
		}
	    return new File(getSkyostDirectory() + File.separator + "SkyoticketsDesktop" + File.separator);
	}
	
	public static final boolean compareVersions(final String versionTo, final String versionWith) {
		final int cmp = normalisedVersion(versionTo, ".", 4).compareTo(normalisedVersion(versionWith, ".", 4));
		if(cmp < 0) {
			return false;
		}
		else if(cmp > 0) {
			return true;
		}
		return false;
	}
	
	private static final String normalisedVersion(final String version, final String separator, final int maxWidth) {
		final StringBuilder stringBuilder = new StringBuilder();
		for(final String normalised : Pattern.compile(separator, Pattern.LITERAL).split(version)) {
			stringBuilder.append(String.format("%" + maxWidth + 's', normalised));
		}
		return stringBuilder.toString();
	}
	
}
