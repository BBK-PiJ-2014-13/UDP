package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class UtilityImpl implements Utility {

	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private int portNumber;
	public int id;
	private DatagramSocket UDPSocket;
	private String onServerFileName;
	byte[] buffer;
	InetAddress IPAddress;

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
		IPAddress = socket.getInetAddress();
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
			fileOutputStream = new FileOutputStream(fileName);
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
					System.out
							.println("Server" + id + ": Connection timed out");
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
		UDPSocket.close();
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getOnServerFileName() {
		return onServerFileName;
	}

	@Override
	public void sendFile(String fileName) {
		buffer = new byte[1024];
		File audioFile = new File(fileName);
		InputStream targetStream = null;
		try {
			targetStream = new FileInputStream(audioFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (targetStream.read(buffer) != -1) {
				DatagramPacket sendPacket = new DatagramPacket(buffer,
						buffer.length, IPAddress, portNumber);
				UDPSocket.send(sendPacket);
			}
			targetStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UDPSocket.close();
	}

}
