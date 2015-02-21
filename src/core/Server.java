package core;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public void registerServer() {
		try {
			Registry reg = LocateRegistry.createRegistry(1099);
			reg.rebind("echangeAudio", new ExchangeAudioServer());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
}
