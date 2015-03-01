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
	 * @return sent String
	 */
	public String sendID(int id);

	/**
	 * Answer for request if first to connect
	 * 
	 * @return answer: true if the client is first to connect, false if not
	 */
	public boolean answerIfFirstToConnect(boolean isFirstToConnect);

	/**
	 * Read name of file from received packet through TCP connection
	 * 
	 * @return name of audio file
	 */
	public String readNameOfFile();
}
