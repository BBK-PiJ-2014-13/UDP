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

public class Client {
	public static void main(String[] args) {
		String hostName = "localhost";
		String sentence = null;
		int id;
		int portNumber = Integer.parseInt(args[0]);
		boolean isFirstToConnect;
		
		try {
			
			System.out.println("=================================");
			System.out.println("Started a client");
			System.out.println("=================================");
			System.out.println();
			
			// Connect to server
			Socket socket = new Socket(hostName, portNumber);

			// Declare readers and writers
			DataOutputStream outToServer = new DataOutputStream(
					socket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

			// Ask for Unique ID
			outToServer.writeBytes("IDrequest\n");
			System.out.println("=================================");
			System.out.println("Ask for a unique ID");
			System.out.println("=================================");
			System.out.println();
			id = Integer.parseInt(inFromServer.readLine());
			System.out.println("=================================");
			System.out.println("Unique ID received:" + id);
			System.out.println("=================================");
			System.out.println();

			// Ask if its first to connect
			System.out.println("=================================");
			System.out.println("Client: Asked if first to Connect");
			System.out.println("=================================");
			System.out.println();
			outToServer.writeBytes("firstToConnectRequest" + "\n");
			isFirstToConnect = Boolean.parseBoolean(inFromServer.readLine());
			System.out.println("=================================");
			System.out.println("Client: Received firstToConnect answer: " + isFirstToConnect);
			System.out.println("=================================");
			System.out.println();

			socket.close();

			// Open UDP connection to server
			DatagramSocket UDPsocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localhost");
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];

			// Send audio
			if (isFirstToConnect) {
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, portNumber);
				UDPsocket.send(sendPacket);
				File audioFile = new File("Non, je ne regrette rien.mp3");
				InputStream targetStream = new FileInputStream(audioFile);
				targetStream.read(sendData);
				targetStream.close();
			}

			// Receive audio
			else {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				UDPsocket.receive(receivePacket);
				FileOutputStream fileOutputStream = new FileOutputStream(
						"inputFile.mp3");
				fileOutputStream.write(receivePacket.getData());
				fileOutputStream.close();
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
