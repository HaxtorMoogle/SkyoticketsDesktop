package fr.skyost.tickets.desktop.threads;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

import fr.skyost.tickets.desktop.Skyotickets;

public class Downloader extends Thread {
	
	private final String url;
	private final File path;
	
	public Downloader(final String url, final File path) {
		this.url = url;
		this.path = path;
	}
	
	@Override
	public void run() {
		try {
			JOptionPane.showMessageDialog(null, "Downloading the user language...", "Skyotickets Desktop", JOptionPane.NO_OPTION);
			final HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
			connection.addRequestProperty("User-Agent", "SkyoticketsDesktop v" + Skyotickets.VERSION);
			connection.setInstanceFollowRedirects(false);
			final String response = connection.getResponseCode() + " " + connection.getResponseMessage();
			if(!response.startsWith("2")) {
				if(response.startsWith("301")) {
					JOptionPane.showMessageDialog(null, "The application has not been translated to your language yet !", "Skyotickets Desktop", JOptionPane.OK_OPTION);
				}
				else {
					JOptionPane.showMessageDialog(null, response, "Bad response", JOptionPane.ERROR_MESSAGE);
				}
				return;
			}
			final InputStream inputStream = connection.getInputStream();
			final FileOutputStream fileOutputStream = new FileOutputStream(path);
			final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);
			final byte[] data = new byte[1024];
			int i = 0;
			while((i = inputStream.read(data, 0, 1024)) >= 0) {
				bufferedOutputStream.write(data, 0, i);
			}
			bufferedOutputStream.close();
			fileOutputStream.close();
			inputStream.close();
			JOptionPane.showMessageDialog(null, "Done, please restart Skyotickets Desktop.", "Skyotickets Desktop", JOptionPane.OK_OPTION);
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, Skyotickets.messages.getProperty("exception-error", "Error !"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
