package core;

import java.net.Socket;

/**
 * @author Ilya Ivanov
 *
 *
 *         Provides methods for Client and ServerThread objects
 */
public interface Utility {
	
	/**
	 * Send id to client if it requested one
	 * 
	 */
	public void sendID();

	/**
	 * Answer for request if first to connect
	 * 
	 * @return answer: true if the client is first to connect, false if not
	 * @param isFirstToConnect
	 *            Whether the client is first to connect or not
	 */
	public boolean answerIfFirstToConnect(boolean isFirstToConnect);

	/**
	 * Read name of file from received packet through TCP connection
	 * 
	 * @return name of audio file
	 */
	public String readNameOfFile();

	/**
	 * Close TCP socket and open UDP
	 * 
	 * @param socket
	 *            TCP socket
	 */
	public void initializeUDP(Socket socket);
	
	/**
	 * @param fileName name of audio file
	 */
	public void setFileName(String fileName);

	/**
	 * Receive file over UDP
	 */
	public void receiveFile();
	
	/**
	 * 
	 * @param id of client that connects to server
	 */
	public void setID(int id);
}
	