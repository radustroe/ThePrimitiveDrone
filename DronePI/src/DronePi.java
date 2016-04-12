import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

import org.omg.CORBA.Request;

import java.awt.Robot;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DronePi {
	public static int SERVER_PORT = 6000;
	public Robot robot;

	public DronePi() {
		try {
			robot = new Robot();
		} catch (Exception e) {
		}
	}

	private ServerSocket goOnline() {

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(SERVER_PORT);

		} catch (IOException E) {
			JOptionPane.showMessageDialog(null, "Server: Error creating network connection", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		try {
			System.out.println("SERVER Online\nWaiting for gestures on: " + "192.168.56.1" + ":" + SERVER_PORT + "\n");

		} catch (Exception e) {
		}
		return serverSocket;

	}

	private void readInput(ServerSocket serverSocket) {
		while (true) {
			Socket socket = null;
			BufferedReader in = null;
			try {
				socket = serverSocket.accept();
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Server: Error connecting to client", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}
			try {
				String request = in.readLine();
				if (request.equals("Spinning")) {
					Runtime rt = Runtime.getRuntime();
					try {
						int counter = 0;
						if (counter%2 == 0) {
							Process pr = rt.exec("./middle.sh");
							
							counter ++;
							System.out.println("alive");
						} else  {
							
							Process pr1 = rt.exec("sudo killall middle.sh");
							System.out.println("dead");
						}

					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			} catch (IOException E) {
				JOptionPane.showMessageDialog(null, "Server: Error creating network connection", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	public static void main(String[] args) {

		DronePi leap = new DronePi();
		ServerSocket ss = leap.goOnline();
		if (leap != null)
			leap.readInput(ss);

	}

}
