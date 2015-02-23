package testers;

import core.Server;

/**
 * @author Ilya Ivanov
 *
 *         Tests main methods in Server and Client
 */
public class ServerTester extends Thread {
	
	public void run() {
		Server.main(new String[] {"1099"});
	}
}
