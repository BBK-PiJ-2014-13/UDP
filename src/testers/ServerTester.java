package testers;

import org.junit.Test;

import core.Server;

public class ServerTester extends BasicTester {
	
	/**
	 * 
	 */
	@Test
	public void mainTester () {
		String[] args = new String[] {"4444"};
		Server.main(args);
		test();
	}
}
