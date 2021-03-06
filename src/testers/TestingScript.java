package testers;

/**
 * @author Ilya Ivanov
 *
 *         Launches main methods of clients and server
 */
public class TestingScript {
	public static void main(String[] args) {
		String portNumber = "1096";

		// Start a server
		ServerTester serverTester = new ServerTester("inputFile.mp3", portNumber);
		serverTester.start();

		// Start a client
		ClientTester clientTester = new ClientTester("audio.mp3", portNumber);
		clientTester.start();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Start another client
		ClientTester clientTester2 = new ClientTester("audio.mp3", portNumber);
		clientTester2.start();

	}
}
