package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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

			// TODO Listen to UDP connection
			DatagramSocket serverSocket = new DatagramSocket(4444);
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				serverSocket.receive(receivePacket);
				String sentence = new String(receivePacket.getData());
				System.out.println("RECEIVED: " + sentence);
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				String capitalizedSentence = sentence.toUpperCase();
				sendData = capitalizedSentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
