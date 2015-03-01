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
	public String sendID(Socket socket, int id);
	
	/**
	 * Answer for request if first to connect
	 * 
	 * @return answer: true if the client is first to connect, false if not 
	 */
	public boolean answerIfFirstToConnect(Socket socket, boolean isFirstToConnect);
}
