package testers;

import core.Server;

/**
 * @author Ilya Ivanov
 *
 *         Tests main methods in Server and Client
 */
public class ServerTester extends Thread {
	String fileName;
	String portNumber;

	public ServerTester(String fileName, String portNumber) {
		this.fileName = fileName;
		this.portNumber = portNumber;
	}

	public void run() {
		Server.main(new String[] { fileName, portNumber });
	}
}
