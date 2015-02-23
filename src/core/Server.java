package core;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	public static void main(String[] args) {
		int portNumber = 4442;
		boolean listening = true;
		int nextID = 0;
		boolean hasFirstConnection = false;
		
		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			while (listening) {
				if (!hasFirstConnection) {
					new ServerThread(serverSocket.accept(), nextID, true).start();
					hasFirstConnection = true;
				} else {
					new ServerThread(serverSocket.accept(), nextID, false).start();
				}
				nextID++;
			}
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
		}
	}
}
