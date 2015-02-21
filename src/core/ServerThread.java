package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket socket = null;
	String clientSentence = null;

	public ServerThread(Socket socket) {
		super("ServerThread");
		this.socket = socket;
	}

	public void run() {
		// TODO Send unique ID
		try {
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
					socket.getOutputStream());
			clientSentence = inFromClient.readLine();
			System.out.println("Received: " + clientSentence);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
