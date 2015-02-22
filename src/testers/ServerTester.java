package testers;

import org.junit.Test;

import core.Server;

public class ServerTester extends BasicTester {
	
	/**
	 * 
	 */
	@Test
	public void mainTester () {
		Server server = new Server();
		String[] args = new String[] {"4444"};
		server.main(args);
		test();
	}
}
