package core;

import java.net.Socket;

public class ServerThread extends Thread {
	private Socket TCPSocket = null;
	Utility utility;
	boolean isFirstToConnect;

	public ServerThread(Socket socket, int id, boolean isFirstToConnect,
			String onServerFileName) {
		this.TCPSocket = socket;
		this.isFirstToConnect = isFirstToConnect;
		utility = new UtilityImpl(TCPSocket);
		utility.setOnServerFileName(onServerFileName);
		utility.setID(id);
	}

	public void run() {

		// Send unique ID
		utility.sendID();

		// Indicate to client if it is a sender or receiver process
		utility.answerIfFirstToConnect(isFirstToConnect);

		// Receive name of file and remember it
		utility.setOnServerFileName(utility.readNameOfFile());

		// Close TCP connection and open UDP
		utility.initializeUDP(TCPSocket);

		
		// UDP Section
		
		// Receive
		if (isFirstToConnect) {
			utility.receiveFile(utility.getOnServerFileName());
		}

		// Send
		else {
			utility.sendFile(utility.getOnServerFileName());
		}

	}
}
