package testers;

/**
 * @author Ilya Ivanov
 *
 *         Launches main methods of clients and server
 */
public class TestingScript {
	public static void main(String[] args) {
		String fileName = "outputFile.mp3";
		String portNumber = "1054";

		// Start a server
		ServerTester serverTester = new ServerTester(portNumber);
		serverTester.start();

		// Start a client
		ClientTester clientTester = new ClientTester(fileName, portNumber);
		clientTester.start();

	}
}
