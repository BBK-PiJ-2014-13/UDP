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
import java.net.UnknownHostException;
import java.util.Arrays;

public class Client {
	public static void main(String[] args) {
		String hostName = "localhost";
		String fileName = args[0];
		int id;
		int portNumber = Integer.parseInt(args[1]);
		boolean isFirstToConnect = false;

		try {
			// Connect to server
			Socket TCPsocket = new Socket(hostName, portNumber);

			// Declare readers and writers
			DataOutputStream outToServer = new DataOutputStream(
					TCPsocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(TCPsocket.getInputStream()));

			// Ask for Unique ID
			outToServer.writeBytes("IDrequest\n");
			id = Integer.parseInt(inFromServer.readLine());

			// Ask if its first to connect
			outToServer.writeBytes("firstToConnectRequest" + "\n");
			isFirstToConnect = Boolean.parseBoolean(inFromServer.readLine());

			// Send the name of file to Server
			outToServer.writeBytes(fileName + "\n");

			TCPsocket.close();

			// UDP
			DatagramSocket UDPSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localhost");
			byte[] buffer;

			// Send audio
			if (isFirstToConnect) {
				buffer = new byte[1024];
				File audioFile = new File(fileName);
				InputStream targetStream = new FileInputStream(audioFile);
				while (targetStream.read(buffer) != -1) {
					DatagramPacket sendPacket = new DatagramPacket(buffer,
							buffer.length, IPAddress, portNumber);
					UDPSocket.send(sendPacket);
				}
				targetStream.close();
			}

			// Receive audio
			else {
				File clientFile = new File("client" + id + fileName);
				FileOutputStream fileOutputStream = new FileOutputStream(clientFile);
				boolean keepGoing = true;
				while (keepGoing) {
					buffer = new byte[1024];
					DatagramPacket receivePacket = new DatagramPacket(buffer,
							buffer.length);
					
					try {
						UDPSocket.receive(receivePacket);
					} catch (SocketTimeoutException e) {
						System.out
								.println("Server" + id + ": Connection timed out");
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
			UDPSocket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
