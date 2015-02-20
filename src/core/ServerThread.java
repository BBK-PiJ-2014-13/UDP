package core;

import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket socket = null;

	public ServerThread(Socket socket) {
		super("ServerThread");
		this.socket = socket;
	}

	public void run() {

	try {
		socket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
