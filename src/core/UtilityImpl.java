package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class UtilityImpl implements Utility {

	private BufferedReader inFromClient;
	private DataOutputStream outToClient;

	@Override
	public String sendID(Socket socket) {
		try {
			inFromClient = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outToClient = new DataOutputStream(socket.getOutputStream());
			if (inFromClient.readLine().equals("IDrequest")) {
				outToClient.writeBytes(Integer.toString(id) + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
