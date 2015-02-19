package core;

import java.rmi.Remote;

public interface MyRemote extends Remote {
	
	public void sendAudio(Object o);

}
