package core;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	public static void main(String[] args) {
		int portNumber = Integer.parseInt(args[0]);
		boolean listening = true;
		int nextID = 0;
		boolean hasFirstConnection = false;
		
		System.out.println("=================================");
		System.out.println("Started a server");
		System.out.println("=================================");
		System.out.println();
		
		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			while (listening) {
//				System.out.println("hasFirstConnection: " + hasFirstConnection);
				if (!hasFirstConnection) {
					System.out.println("gets called");
					new ServerThread(serverSocket.accept(), nextID, true).start();
					hasFirstConnection = true;
				} else {
//					new ServerThread(serverSocket.accept(), nextID, false).start();
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
