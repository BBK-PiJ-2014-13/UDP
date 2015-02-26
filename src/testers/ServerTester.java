package testers;

import core.Server;

/**
 * @author Ilya Ivanov
 *
 *         Tests main methods in Server and Client
 */
public class ServerTester extends Thread {
	String portNumber;

	public ServerTester(String portNumber) {
		this.portNumber = portNumber;
	}

	public void run() {
		Server.main(new String[] { portNumber });
	}
}
