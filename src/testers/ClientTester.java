package testers;

import core.Client;

public class ClientTester extends Thread {
	
	public void run() {
		Client.main(new String[] {"1099"});
	}

}
