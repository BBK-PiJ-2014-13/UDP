package core;

import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		int portNumber = 4444;
		
		Socket socket = new Socket(hostName, portNumber);
	}
}
