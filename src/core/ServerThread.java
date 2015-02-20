package core;

import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket socket = null;

	public ServerThread(Socket socket) {
		super("ServerThread");
		this.socket = socket;
	}

	public void run() {
		// TODO Send unique ID
		// TODO Tell client if its sender or receiver
		// TODO Listen to UDP connection
		// TODO Tell client to connect over UDP
		// TODO Relay Audio data
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
