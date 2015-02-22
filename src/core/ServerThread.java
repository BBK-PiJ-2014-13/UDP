package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket socket = null;
	String clientSentence = null;
	int id = 0;
	boolean isFirstToConnect;

	public ServerThread(Socket socket, int id, boolean isFirstToConnect) {
		super("ServerThread");
		this.socket = socket;
		this.id = id;
		this.isFirstToConnect = isFirstToConnect;
	}

	public void run() {
		try {

			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
					socket.getOutputStream());

			// Send unique ID
			if (inFromClient.readLine() == "IDrequest") {
				outToClient.writeBytes(Integer.toString(id));
			}

			// Indicate to client if it is a sender or receiver process
			if (inFromClient.readLine() == "firstToConnectRequest") {
				outToClient.writeBytes(Boolean.toString(isFirstToConnect));
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
