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

import lombok.Data;

@Data
public class UtilityImpl implements Utility {

	private BufferedReader inFrom;
	private DataOutputStream outTo;
	private int portNumber;
	private int id;
	private Socket TCPSocket;
	private DatagramSocket UDPSocket;
	private String onServerFileName;
	byte[] buffer;
	InetAddress IPAddress;

	public UtilityImpl(Socket socket) {
		try {
			inFrom = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outTo = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		portNumber = socket.getLocalPort();
		IPAddress = socket.getInetAddress();
		TCPSocket = socket;
	}

	@Override
	public void sendID() {
		
		try {
			if (inFrom.readLine().equals("IDrequest")) {
				outTo.writeBytes(Integer.toString(id) + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int askForID() {
		int result = 0;
		try {
			outTo.writeBytes("IDrequest\n");
			result = Integer.parseInt(inFrom.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean answerIfFirstToConnect(boolean isFirstToConnect) {
		try {
			if (inFrom.readLine().equals("firstToConnectRequest")) {
				outTo.writeBytes(Boolean.toString(isFirstToConnect) + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isFirstToConnect;
	}

	@Override
	public boolean askIfFirstToConnect() {
		boolean result = false;
		try {
			outTo.writeBytes("firstToConnectRequest" + "\n");
			result = Boolean.parseBoolean(inFrom.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void initializeUDP() {
		try {
			TCPSocket.close();
			UDPSocket = new DatagramSocket(portNumber);
			UDPSocket.setSoTimeout(2000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				} else {
					e.printStackTrace();
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
				try {
				UDPSocket.send(sendPacket);
				} catch (NullPointerException e) {
					if (UDPSocket == null) {
						System.out.println("UDPSocket");
					}
					
					if (sendPacket == null) {
						System.out.println("sendPacket");
					}
					
					throw e;
				}
			}
			targetStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
