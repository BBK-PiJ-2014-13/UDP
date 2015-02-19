package core;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemote extends Remote {
	
	public void sendAudio(Object o) throws RemoteException;

}
