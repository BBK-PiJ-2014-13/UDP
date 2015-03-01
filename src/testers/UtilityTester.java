package testers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
		serverUtility.setID(35);
		outToServer.writeBytes("IDrequest\n");
		serverUtility.sendID();
		valueExpected = 35;
		valueActual = Integer.parseInt(inFromServer.readLine());
		test();
	}
	
	/**
	 * Tests askForID()
	 * @throws IOException 
	 */
	@Test
	public void askForIDTester() throws IOException {
		outToClient.writeBytes(Integer.toString(50) + "\n");
		valueExpected = 50;
		valueActual = clientUtility.askForID();
		test();
	}

}
