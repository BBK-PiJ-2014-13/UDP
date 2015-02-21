package core;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ExchangeAudioService extends Remote {
	
	public void exchangeAudio() throws RemoteException;

}
