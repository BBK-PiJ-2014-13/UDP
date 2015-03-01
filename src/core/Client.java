package core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) {
		String originalFileName = args[0];
		int id;
		boolean isFirstToConnect = false;
		UtilityImpl utility = null;
		try {
			utility = new UtilityImpl(new Socket("localhost",
					Integer.parseInt(args[1])));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			// Ask for Unique ID
			id = utility.askForID();

			// Ask if its first to connect
			isFirstToConnect = utility.askIfFirstToConnect();

			// UDP Section
			utility.initializeUDP("client");
			utility.setIPAddress(InetAddress.getByName("localhost"));

			// Send audio
			if (isFirstToConnect) {
				utility.sendFile(originalFileName);
			}

			// Receive audio
			else {
				utility.receiveFile("Client" + id + originalFileName);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
