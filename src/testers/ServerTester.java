package testers;

import core.Server;

/**
 * @author Ilya Ivanov
 *
 *         Tests main methods in Server and Client
 */
public class ServerTester extends Thread {
	String portnumber;

	public ServerTester(String portumber) {
		this.portnumber = portumber;
	}
	
	public void run() {
		Server.main(new String[] {portnumber});
	}
}
