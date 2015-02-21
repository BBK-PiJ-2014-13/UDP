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
		String modifiedSentence = null;
		int portNumber = 4444;

		try {
			Socket socket = new Socket(hostName, portNumber);
			// TODO Ask for Unique ID
			BufferedReader inFromUser = new BufferedReader(
					new InputStreamReader(System.in));
			DataOutputStream outToServer = new DataOutputStream(
					socket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			sentence = inFromUser.readLine();
			outToServer.writeBytes(sentence + '\n');
			modifiedSentence = inFromServer.readLine();
			System.out.println("FROM SERVER: " + modifiedSentence);
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
