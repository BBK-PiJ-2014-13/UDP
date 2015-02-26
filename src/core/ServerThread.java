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
import java.util.Arrays;

public class ServerThread extends Thread {
	private Socket TCPSocket = null;
	String clientSentence = null;
	String fileName;
	int id = 0;
	boolean isFirstToConnect;

	public ServerThread(Socket socket, int id, boolean isFirstToConnect) {
		super("ServerThread");
		this.TCPSocket = socket;
		this.id = id;
		this.isFirstToConnect = isFirstToConnect;
	}

	public void run() {

		System.out.println("=================================");
		System.out.println("Started a server thread");
		System.out.println("ID: " + id);
		System.out.println("isFirstToConnect: " + isFirstToConnect);
		System.out.println("=================================");
		System.out.println();

		try {
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(TCPSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
					TCPSocket.getOutputStream());

			// Send unique ID
			if (inFromClient.readLine().equals("IDrequest")) {
				outToClient.writeBytes(Integer.toString(id) + "\n");
				System.out.println("=================================");
				System.out.println("Server: Unique id sent: " + id);
				System.out.println("=================================");
				System.out.println();
			}

			// Indicate to client if it is a sender or receiver process
			if (inFromClient.readLine().equals("firstToConnectRequest")) {
				outToClient.writeBytes(Boolean.toString(isFirstToConnect)
						+ "\n");
			}
			
			// Receive name of file
			fileName = inFromClient.readLine();
			System.out.println("=================================");
			System.out.println("SERVER" + id + " : received file: " + fileName);
			System.out.println("=================================");
			System.out.println();
			
			TCPSocket.close();

			// UDP
			DatagramSocket UDPSocket = new DatagramSocket(
					TCPSocket.getLocalPort());
			boolean keepGoing = true;
			// UDPSocket.setSoTimeout(1000);

			System.out.println("=================================");
			System.out
					.println("SERVER THREAD: opened a UDP connection using port"
							+ TCPSocket.getLocalPort());
			System.out.println("=================================");
			System.out.println();

			File serverFile = new File("server" + id + "inputFile.mp3");
			FileOutputStream fileOutputStream = new FileOutputStream(serverFile);

			// Receive audio
			while (keepGoing) {
				byte[] receiveData = new byte[1024];

				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				UDPSocket.receive(receivePacket);

				// If done receiving, stop the while loop
				if (receivePacket.getData() == null
						|| receivePacket.getData().length == 0) {
					keepGoing = false;
				}
				fileOutputStream.write(receivePacket.getData());

			}
			fileOutputStream.close();
			UDPSocket.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
