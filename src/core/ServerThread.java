package core;

import java.net.Socket;

public class ServerThread extends Thread {
	private Socket TCPSocket = null;
	UtilityImpl utility;
	boolean isFirstToConnect;

	public ServerThread(Socket socket, int id, boolean isFirstToConnect,
			String onServerFileName) {
		this.TCPSocket = socket;
		this.isFirstToConnect = isFirstToConnect;
		utility = new UtilityImpl(TCPSocket);
		utility.setOnServerFileName(onServerFileName);
		utility.setId(id);
	}

	public void run() {

		// Send unique ID
		utility.sendID();

		// Indicate to client if it is a sender or receiver process
		utility.answerIfFirstToConnect(isFirstToConnect);

		// UDP Section
		utility.initializeUDP("server");
		
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
