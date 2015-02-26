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
			DatagramSocket UDPsocket = new DatagramSocket();

			while (true) {
				InetAddress IPAddress = InetAddress.getByName("localhost");
				byte[] sendData = new byte[1024];
				byte[] receiveData = new byte[1024];

				// Send audio
				if (isFirstToConnect) {
					File audioFile = new File(fileName);
					InputStream targetStream = new FileInputStream(audioFile);
					while (targetStream.read(sendData) != -1) {
						DatagramPacket sendPacket = new DatagramPacket(sendData,
								sendData.length, IPAddress, portNumber);
						UDPsocket.send(sendPacket);
					}
					targetStream.close();
				}

				// Receive audio
				else {
					// DatagramPacket receivePacket = new
					// DatagramPacket(receiveData,
					// receiveData.length);
					// UDPsocket.receive(receivePacket);
					// FileOutputStream fileOutputStream = new FileOutputStream(
					// "inputFile.mp3");
					// fileOutputStream.write(receivePacket.getData());
					// fileOutputStream.close();
				}
				break;
			}
			UDPsocket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
