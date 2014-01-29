package fr.skyost.tickets.desktop.threads;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import fr.skyost.tickets.desktop.Skyotickets;
import fr.skyost.tickets.desktop.frames.CommandFrame;
import fr.skyost.tickets.desktop.frames.OutputFrame;

public class RemoteControl extends Thread {
	
	private JFrame jFrame;
	private String host;
	private String port;
	private String command;
	
	private Socket socket;
	
	public RemoteControl(final JFrame jFrame, final String host, final String port, final String command) throws IOException {
		this.jFrame = jFrame;
		this.host = host;
		this.port = port;
		this.command = command;
	}
	
	@Override
	public void run() {
		final String response;
		final String title = jFrame.getTitle();
		jFrame.setTitle("Please wait...");
		try {
			socket = new Socket(host, Integer.parseInt(port));
			socket.setSoTimeout(Integer.valueOf(Skyotickets.properties.getProperty("timeOut")));
			final PrintWriter sender = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			sender.println(command);
			final BufferedReader receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			response = receiver.readLine();
			sender.close();
			receiver.close();
			socket.close();
			if(title.equals(host + ":" + port)) {
				OutputFrame.getInstance().addText(response);
				CommandFrame.resetTxtCommand();
			}
			else {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						try {
							if(response.contains("You are authenticated")) {
								if(OutputFrame.getInstance() == null) {
									new OutputFrame().setVisible(true);
								}
								CommandFrame commandFrame = new CommandFrame();
								commandFrame.setTitle(host + ":" + port);
								commandFrame.setLocationRelativeTo(null);
								commandFrame.setVisible(true);
								Skyotickets.hide(jFrame);
								Skyotickets.close(jFrame);
							}
							else {
								JOptionPane.showMessageDialog(jFrame, response, "Bad response !", JOptionPane.ERROR_MESSAGE);
							}
						}
						catch(Exception ex) {
							JOptionPane.showMessageDialog(jFrame, ex, "Error !", JOptionPane.ERROR_MESSAGE);
						}
					}
					
				});
			}
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(jFrame, ex, "Error !", JOptionPane.ERROR_MESSAGE);
		}
		jFrame.setTitle(title);
	}
	
}
