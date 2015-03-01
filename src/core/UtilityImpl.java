package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.junit.Before;

public class UtilityImpl implements Utility {

	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private Socket socket;
	
	public UtilityImpl(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public String sendID(int id) {
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

	@Override
	public boolean answerIfFirstToConnect(boolean isFirstToConnect) {
		try {
			if (inFromClient.readLine().equals("firstToConnectRequest")) {
				outToClient.writeBytes(Boolean.toString(isFirstToConnect)
						+ "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isFirstToConnect;
	}

}
