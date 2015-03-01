package testers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import core.Utility;
import core.UtilityImpl;

public class UtilityTester extends BasicTester {
	int portNumber = 1099;
	ServerSocket serverSocket;
	Socket clientSocket;
	Utility serverUtility;
	Utility clientUtility;

	@Before
	public void buildUp() throws IOException {
		serverSocket = new ServerSocket(portNumber);
		clientSocket = new Socket("localhost", portNumber);
		serverUtility = new UtilityImpl(serverSocket.accept());
		clientUtility = new UtilityImpl(clientSocket);

	}

	/**
	 * Tests sendID() and askForID()
	 */
	@Test
	public void sendAndAskForIDTester() {
		clientUtility.askForID();
		serverUtility.sendID();
	}

}
