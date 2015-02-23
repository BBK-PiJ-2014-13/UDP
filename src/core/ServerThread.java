package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

			socket.close();

			// UDP
			DatagramSocket UDPSocket = new DatagramSocket(4444);
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			int port = 0;

			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			UDPSocket.receive(receivePacket);
			InetAddress IPAddress = receivePacket.getAddress();
			port = receivePacket.getPort();
			receivePacket.getData();
			// Receive audio
			if (isFirstToConnect) {
				FileOutputStream fileOutputStream = new FileOutputStream(
						"serverInputFile.mp3");
				fileOutputStream.write(receivePacket.getData());
				fileOutputStream.close();
			}

			// Send audio
			else {
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, port);
				UDPSocket.send(sendPacket);
			}
			UDPSocket.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
