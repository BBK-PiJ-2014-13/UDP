package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerThread extends Thread {
	private Socket TCPSocket = null;
	String clientSentence = null;
	String fileName;
	String inputFile; // Name of file copied from client and then sent to other
						// clients
	Utility utility;
	int id = 0;
	boolean isFirstToConnect;
	int portNumber;
	InetAddress IPAddress;

	public ServerThread(Socket socket, int id, boolean isFirstToConnect,
			String inputFile) {
		super("ServerThread");
		this.TCPSocket = socket;
		this.id = id;
		this.isFirstToConnect = isFirstToConnect;
		this.inputFile = inputFile;
		portNumber = socket.getLocalPort();
		IPAddress = socket.getInetAddress();
		utility = new UtilityImpl(TCPSocket);
	}

	public void run() {

		try {
			// TCP section
			
			// Send unique ID
			utility.sendID(id);

			// Indicate to client if it is a sender or receiver process
			utility.answerIfFirstToConnect(isFirstToConnect);

			// Receive name of file
			fileName = utility.readNameOfFile();

			// Close TCP connection and open UDP
			utility.initializeUDP(TCPSocket);

			
			// UDP Section
			
			boolean keepGoing = true;
			File serverFile = new File(inputFile);
			byte[] buffer;

			// Receive
			if (isFirstToConnect) {
				FileOutputStream fileOutputStream = new FileOutputStream(
						serverFile);
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
				// Receive an empty packet to get client's address
				buffer = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(buffer,
						buffer.length);
				try {
					UDPSocket.receive(receivePacket);
				} catch (SocketTimeoutException e) {
					System.out.println("Server" + id
							+ ": Connection timed out");
				}
				
				IPAddress = receivePacket.getAddress();
				
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
