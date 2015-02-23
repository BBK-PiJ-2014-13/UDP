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

		// Start a server
		ServerTester serverTester = new ServerTester(portnumber);
		serverTester.start();

		// Start a client
		ClientTester clientTester = new ClientTester(portnumber);
		clientTester.start();
	}
}
