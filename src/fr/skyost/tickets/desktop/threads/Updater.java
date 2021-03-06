package fr.skyost.tickets.desktop.threads;

import java.awt.Desktop;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import fr.skyost.tickets.desktop.Skyotickets;
import fr.skyost.tickets.desktop.utils.Utils;

public class Updater extends Thread {
	
	private final String PAGE_URL = "http://dev.bukkit.org/bukkit-plugins/skyotickets/pages/remote-controlling/multi-platform/";
	
	@Override
	public void run() {
		try {
			final Connection connection = (HttpConnection)Jsoup.connect(PAGE_URL);
			connection.data("query", "Java");
			connection.userAgent("SkyoticketsDesktop v" + Skyotickets.VERSION);
			connection.timeout(10000);
			final Document page = connection.get();
			String remoteVersion = null;
			String elementText;
			for(Element element : page.getElementById("bd").getElementsByTag("u")) {
				elementText = element.text();
				if(elementText.contains("Current version")) {
					remoteVersion = elementText.split("Current version : v")[1];
					remoteVersion = remoteVersion.substring(0, remoteVersion.length() - 1);
					break;
				}
			}
			if(remoteVersion == null) {
				return;
			}
			if(Utils.compareVersions(remoteVersion, Skyotickets.VERSION)) {
				JEditorPane jEditorPane = new JEditorPane("text/html", "<html>An update has been found : v" + remoteVersion + " !<br>You can the download the new version <a href=\""+ PAGE_URL +"\">here</a>.</html>");
				jEditorPane.addHyperlinkListener(new HyperlinkListener() {

					@Override
					public void hyperlinkUpdate(final HyperlinkEvent event) {
						if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
							if(Desktop.isDesktopSupported()) {
								try {
									Desktop.getDesktop().browse(event.getURL().toURI());
								}
								catch(Exception ex) {
									JOptionPane.showMessageDialog(null, ex, "Error while checking for updates !", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
					
				});
				jEditorPane.setEditable(false);
				JOptionPane.showMessageDialog(null, jEditorPane, "Update available !", JOptionPane.OK_OPTION);
			}
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "Error while checking for updates !", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
