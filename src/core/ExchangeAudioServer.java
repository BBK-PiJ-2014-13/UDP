package core;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ExchangeAudioServer extends UnicastRemoteObject implements ExchangeAudioService {

	protected ExchangeAudioServer() throws RemoteException {
	}

	@Override
	public void exchangeAudio() throws RemoteException {
	}

}
