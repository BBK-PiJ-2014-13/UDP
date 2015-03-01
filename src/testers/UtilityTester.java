package testers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.UtilityImpl;

public class UtilityTester extends BasicTester {
	int portNumber = 1095;
	ServerSocket serverSocket;
	Socket client;
	Socket server;
	UtilityImpl serverUtility;
	UtilityImpl clientUtility;
	BufferedReader inFromServer;
	BufferedReader inFromClient;
	DataOutputStream outToServer;
	DataOutputStream outToClient;
	byte[] buffer;

	@Before
	public void buildUp() throws IOException {
		serverSocket = new ServerSocket(portNumber);
		client = new Socket("localhost", portNumber);
		server = serverSocket.accept();
		serverUtility = new UtilityImpl(server);
		clientUtility = new UtilityImpl(client);
		inFromServer = new BufferedReader(new InputStreamReader(
				client.getInputStream()));
		inFromClient = new BufferedReader(new InputStreamReader(
				server.getInputStream()));
		outToServer = new DataOutputStream(client.getOutputStream());
		outToClient = new DataOutputStream(server.getOutputStream());
	}

	@After
	public void wrapUp() throws IOException {
		client.close();
		server.close();
		serverSocket.close();
	}

	@Test
	public void sendIDTester() throws IOException {
		serverUtility.setId(35);
		outToServer.writeBytes("IDrequest\n");
		serverUtility.sendID();
		valueExpected = 35;
		valueActual = Integer.parseInt(inFromServer.readLine());
		test();
	}

	/**
	 * Tests askForID()
	 * 
	 * @throws IOException
	 */
	@Test
	public void askForIDTester() throws IOException {
		outToClient.writeBytes(Integer.toString(50) + "\n");
		valueExpected = 50;
		valueActual = clientUtility.askForID();
		test();
	}

	/**
	 * tests answerIfFirstToConnect()
	 * 
	 * @throws IOException
	 */
	@Test
	public void answerIfFirstToConnectTester() throws IOException {
		outToServer.writeBytes("firstToConnectRequest" + "\n");
		valueExpected = true;
		valueActual = serverUtility.answerIfFirstToConnect(true);
		test();
		outToServer.writeBytes("firstToConnectRequest" + "\n");
		valueExpected = false;
		valueActual = serverUtility.answerIfFirstToConnect(false);
		test();
	}

	/**
	 * tests askIfFirstToConnect()
	 * 
	 * @throws IOException
	 */
	@Test
	public void askIfFirstToConnectTester() throws IOException {
		outToClient.writeBytes("true\n");
		valueExpected = true;
		valueActual = clientUtility.askIfFirstToConnect();
		test();
		outToClient.writeBytes("false\n");
		valueExpected = false;
		valueActual = clientUtility.askIfFirstToConnect();
		test();
	}

	/**
	 * tests receiveFile()
	 * 
	 * @throws IOException
	 */
	
	/**
	 * tests receiveFile() and sendFile()
	 */
	@Test
	public void receiveSendFileTester() {
		test();
	}

//	@Test
	public void receiveFileTester() throws IOException {
		clientUtility.initializeUDP("client");
		serverUtility.initializeUDP("server");
		
		// Send
		buffer = new byte[1024];
		File audioFile = new File("testFirstFile.txt");
		InputStream targetStream = new FileInputStream(audioFile);
		while (targetStream.read(buffer) != -1) {
			DatagramPacket sendPacket = new DatagramPacket(buffer,
					buffer.length, clientUtility.getIPAddress(), portNumber);
			serverUtility.getUDPSocket().send(sendPacket);
		}
		targetStream.close();
		
		serverUtility.receiveFile("testSecondFile.txt");
		valueExpected = new File("testFirstFile").length();
		valueActual = new File("testSecondFile").length();
		test();
	}

	/**
	 * tests sendFile()
	 * 
	 * @throws IOException
	 */
	@Test
	public void sendFileTester() throws IOException {
		clientUtility.initializeUDP("client");
		clientUtility.setIPAddress(InetAddress.getByName("localhost"));
		serverUtility.initializeUDP("server");
		clientUtility.sendFile("testFirstFile.txt");

		// Receive
		boolean keepGoing = true;
		FileOutputStream fileOutputStream = new FileOutputStream(
				"testSecondFile.txt");
		int count = 0;
		while (keepGoing) {
			System.out.println(count++);
			buffer = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(buffer,
					buffer.length);
			try {
				serverUtility.getUDPSocket().receive(receivePacket);
			} catch (IOException e) {
				if (e instanceof SocketTimeoutException) {
					System.out.println("Server" + ": Connection timed out");
					break;
				} else {
					e.printStackTrace();
				}
			} catch (NullPointerException e) {
				if (serverUtility.getUDPSocket() == null) {
					System.out.println("UDP SOCKET");
				}
				e.printStackTrace();
				throw new NullPointerException();
			}

			fileOutputStream.write(receivePacket.getData());

			// If done receiving, stop the while loop
			if (receivePacket.getData() == null
					|| receivePacket.getData().length == 0) {
				keepGoing = false;
			}
		}
		fileOutputStream.close();

		valueExpected = new File("testFirstFile.txt").length();
		valueActual = new File("testSecondFile.txt").length();
		test();
	}

}
