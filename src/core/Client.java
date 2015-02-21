package core;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) {
		String hostName = "host";
		String sentence;
		int portNumber = 4444;
		
		try {
			Socket socket = new Socket(hostName, portNumber);
			// TODO Ask for Unique ID
			// TODO Ask if its first to connect
			// TODO Open UDP connection to server
				// Send or receive audio
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
