package testers;

import core.Client;

public class ClientTester extends Thread {

	String portnumber;

	public ClientTester(String portumber) {
		this.portnumber = portumber;
	}

	public void run() {
		Client.main(new String[] { portnumber });
	}

}
