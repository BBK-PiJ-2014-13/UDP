package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) {
		String hostName = "host";
		String sentence = null;
		int id;
		int portNumber = 4444;

		try {
			
			// Connect to server
			Socket socket = new Socket(hostName, portNumber);
			
			DataOutputStream outToServer = new DataOutputStream(
					socket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			
			// TODO Ask for Unique ID
			outToServer.writeBytes("IDrequest");
			id = Integer.parseInt(inFromServer.readLine());
			
			socket.close();
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
