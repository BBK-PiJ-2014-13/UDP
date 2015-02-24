package testers;

/**
 * @author Ilya Ivanov
 *
 *         Launches main methods of clients and server
 */
public class StartingScript {
	public static void main(String[] args) {
		String portnumber = "1077";

		// Start a server
		ServerTester serverTester = new ServerTester(portnumber);
		serverTester.start();

		// Start a client
		ClientTester clientTester = new ClientTester(portnumber);
		clientTester.start();

//		// Start a second client
//		ClientTester clientTester2 = new ClientTester(portnumber);
//		clientTester.start();
	}
}
