package core;

/**
 * @author Ilya Ivanov
 *
 *	Launches main methods of clients and server
 */
public class StartingScript {
	public static void main(String[] args) {
		String portnumber = "1099";
		String[] arguments = new String[] {portnumber};
		
		// Start a server
		Server.main(arguments);
		
		// Start a client
		Client.main(arguments);
	}
}
