package core;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	public static void main(String[] args) {
		int portNumber = 4444;
		boolean listening = true;
		
		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			while (listening) {
				new ServerThread(serverSocket.accept()).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
