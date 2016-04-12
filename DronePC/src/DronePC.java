import com.leapmotion.leap.*;


import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;

public class DronePC extends Listener {

	private Socket socket;
	private PrintWriter out;
	private static int SERVER_PORT = 6000;

	public void onConnect(Controller controller) {
		System.out.println("Connected");
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);

	}

	public void onFrame(Controller controller) {
		Frame frame = controller.frame();
		for (Gesture g : frame.gestures()) {
			if (g.type() == Gesture.Type.TYPE_CIRCLE) {
				mediumVelocity();
				try {
					Thread.sleep(50);
				} catch (Exception e) {
				}

			}
		}
	}
	
	private void connectToServer() {
		try {
			socket = new Socket("192.168.0.101", SERVER_PORT); // hostname -I
																// InetAdress.getLocalHost()
			out = new PrintWriter(socket.getOutputStream());
	
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "CLIENT: Cannot connect to server", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}

	}
	
	private void disconnectFromServer() {
		try {

			socket.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "CLIENT: Cannot disconnect from the server", "Error",
					JOptionPane.ERROR_MESSAGE);

		}
	}
	
	private void mediumVelocity() {
		connectToServer();
		out.println("Spinning");
		out.flush();
		disconnectFromServer();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DronePC leap = new DronePC();
		Controller controller = new Controller();
		controller.addListener(leap);

		try {
			System.in.read();

		} catch (Exception e) {
		}

		controller.removeListener(leap);
	}

}