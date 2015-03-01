package core;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	
	/**
	 * Ask server for unique ID
	 */
	public int askForID();
	
	/**
	 * @param isFirstToConnect
	 * @return if client is first to onnect
	 */
	public boolean answerIfFirstToConnect(boolean isFirstToConnect);
	
	/**
	 * @return if client is first to connect
	 */
	public boolean askIfFirstToConnect();

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
	 * @param fileName
	 *            name of audio file
	 */
	public void setOnServerFileName(String fileName);

	/**
	 * Receive file over UDP
	 * 
	 * @param fileName
	 *            Write received file under this name
	 */
	public void receiveFile(String fileName);

	/**
	 * Send file
	 * @param fileName name of the file to send
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public void sendFile(String fileName);
	
	/**
	 * 
	 * @param id
	 *            of client that connects to server
	 */
	public void setID(int id);
	
	/**
	 * @return name of file on server
	 */
	public String getOnServerFileName();
}
