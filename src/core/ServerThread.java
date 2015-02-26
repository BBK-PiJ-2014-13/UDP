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
				System.out.println("=================================");
				System.out.println("Server: firstToConnectanswer is:"
						+ isFirstToConnect);
				System.out.println("=================================");
				System.out.println();
			}
			TCPSocket.close();

			// UDP
			DatagramSocket UDPSocket = new DatagramSocket(
					TCPSocket.getLocalPort());
			boolean keepGoing = true;
			UDPSocket.setSoTimeout(1000);
			
			System.out.println("=================================");
			System.out
					.println("SERVER THREAD: opened a UDP connection using port"
							+ TCPSocket.getLocalPort());
			System.out.println("=================================");
			System.out.println();

			while (keepGoing) {
				byte[] receiveData = new byte[1024];
				byte[] sendData = new byte[1024];
				int port = 0;

				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				UDPSocket.receive(receivePacket);
				InetAddress IPAddress = receivePacket.getAddress();
				port = receivePacket.getPort();

				// Receive audio
				if (isFirstToConnect) {
					File serverFile = new File("server" + id + "inputFile.jpg");
					FileOutputStream fileOutputStream = new FileOutputStream(
							serverFile);
					fileOutputStream.write(receivePacket.getData());
					fileOutputStream.close();
					System.out.println("=================================");
					System.out.println("Server" + id + ": " + "received file");
					System.out.println("=================================");
					System.out.println();
				}

				// Send audio
				else {
					// DatagramPacket sendPacket = new DatagramPacket(sendData,
					// sendData.length, IPAddress, port);
					// UDPSocket.send(sendPacket);
				}
				break;
			}
			UDPSocket.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
