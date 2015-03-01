package testers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.UtilityImpl;

public class UtilityTester extends BasicTester {
	int portNumber = 1097;
	ServerSocket serverSocket;
	Socket client;
	Socket server;
	UtilityImpl serverUtility;
	UtilityImpl clientUtility;
	BufferedReader inFromServer;
	BufferedReader inFromClient;
	DataOutputStream outToServer;
	DataOutputStream outToClient;

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
	 * tests receiveFile() and sendFile()
	 */
	@Test
	public void receiveSendFileTester() {
		clientUtility.initializeUDP();
		serverUtility.initializeUDP();
		clientUtility.sendFile("audio.mp3");
		serverUtility.receiveFile("serverFileTest.mp3");
		valueExpected = new File("audio.mp3").length();
		valueActual = new File("serverFileTest.mp3").length();
		test();
	}

}
