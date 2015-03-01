package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.junit.Before;

public class UtilityImpl implements Utility {

	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private int portNumber;
	public int id;
	private DatagramSocket UDPSocket;
	private String onServerFileName;
	byte[] buffer;

	public UtilityImpl(Socket socket) {
		try {
			inFromClient = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outToClient = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		portNumber = socket.getLocalPort();
	}

	@Override
	public void sendID() {
		try {
			if (inFromClient.readLine().equals("IDrequest")) {
				outToClient.writeBytes(Integer.toString(id) + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public String readNameOfFile() {
		String result = null;
		try {
			result = inFromClient.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void initializeUDP(Socket socket) {
		try {
			socket.close();
			UDPSocket = new DatagramSocket(portNumber);
			UDPSocket.setSoTimeout(2000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void setOnServerFileName(String fileName) {
		this.onServerFileName = fileName;
	}

	public String getFileName() {
		return onServerFileName;
	}

	
	@Override
	public void receiveFile(String fileName) {
		boolean keepGoing = true;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(
					fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (keepGoing) {
			buffer = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(buffer,
					buffer.length);
			try {
				UDPSocket.receive(receivePacket);
				fileOutputStream.write(receivePacket.getData());
			} catch (IOException e) {
				if (e instanceof SocketTimeoutException) {
					System.out.println("Server" + id
							+ ": Connection timed out");
					break;
				}
			}
			
			// If done receiving, stop the while loop
			if (receivePacket.getData() == null
					|| receivePacket.getData().length == 0) {
				keepGoing = false;
			}
		}
		
		try {
			fileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setID(int id) {
		this.id = id;
	}

	
}
