package core;

import testers.ClientTester;
import testers.ServerTester;

/**
 * @author Ilya Ivanov
 *
 *         Launches main methods of clients and server
 */
public class StartingScript {
	public static void main(String[] args) {
		String portnumber = "1099";
		String[] arguments = new String[] { portnumber };

		// Start a server
		ServerTester serverTester = new ServerTester();
		serverTester.start();

		// Start a client
		ClientTester clientTester = new ClientTester();
		clientTester.start();
	}
}
