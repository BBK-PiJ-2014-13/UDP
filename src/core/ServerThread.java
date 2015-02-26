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
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class ServerThread extends Thread {
	private Socket TCPSocket = null;
	String clientSentence = null;
	String fileName;
	String inputFile;
	int id = 0;
	boolean isFirstToConnect;
	int portNumber;

	public ServerThread(Socket socket, int id, boolean isFirstToConnect, String inputFile) {
		super("ServerThread");
		this.TCPSocket = socket;
		this.id = id;
		this.isFirstToConnect = isFirstToConnect;
		this.inputFile = inputFile;
		portNumber = socket.getLocalPort();
	}

	public void run() {

		try {
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(TCPSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
					TCPSocket.getOutputStream());

			// Send unique ID
			if (inFromClient.readLine().equals("IDrequest")) {
				outToClient.writeBytes(Integer.toString(id) + "\n");
			}

			// Indicate to client if it is a sender or receiver process
			if (inFromClient.readLine().equals("firstToConnectRequest")) {
				outToClient.writeBytes(Boolean.toString(isFirstToConnect)
						+ "\n");
			}

			// Receive name of file
			fileName = inFromClient.readLine();

			TCPSocket.close();

			// UDP
			DatagramSocket UDPSocket = new DatagramSocket(
					TCPSocket.getLocalPort());
			boolean keepGoing = true;
			UDPSocket.setSoTimeout(1000);
			File serverFile = new File(inputFile);
			byte[] buffer;
			InetAddress IPAddress = InetAddress.getByName("localhost");

			// Receive
			if (isFirstToConnect) {
				FileOutputStream fileOutputStream = new FileOutputStream(serverFile);
				while (keepGoing) {
					buffer = new byte[1024];

					DatagramPacket receivePacket = new DatagramPacket(buffer,
							buffer.length);
					try {
						UDPSocket.receive(receivePacket);
					} catch (SocketTimeoutException e) {
						System.out.println("Server" + id
								+ ": Connection timed out");
						break;
					}

					// If done receiving, stop the while loop
					if (receivePacket.getData() == null
							|| receivePacket.getData().length == 0) {
						keepGoing = false;
					}

					fileOutputStream.write(receivePacket.getData());

				}
				fileOutputStream.close();
			}

			// Send
			else {
				buffer = new byte[1024];
				File audioFile = new File(inputFile);
				InputStream targetStream = new FileInputStream(audioFile);
				while (targetStream.read(buffer) != -1) {
					DatagramPacket sendPacket = new DatagramPacket(buffer,
							buffer.length, IPAddress, portNumber);
					UDPSocket.send(sendPacket);
				}
				targetStream.close();
			}
			UDPSocket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
