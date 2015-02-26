package testers;

import core.Client;

public class ClientTester extends Thread {

	String fileName;
	String portNumber;

	public ClientTester(String fileName, String portNumber) {
		this.fileName = fileName;
		this.portNumber = portNumber;
	}

	public void run() {
		Client.main(new String[] { fileName, portNumber });
	}

}
