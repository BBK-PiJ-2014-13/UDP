package core;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ExchangeAudioServer extends UnicastRemoteObject implements ExchangeAudioService {

	protected ExchangeAudioServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exchangeAudio() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
