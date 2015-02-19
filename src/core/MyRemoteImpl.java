package core;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

	protected MyRemoteImpl() throws RemoteException {
		super();

		try {
			MyRemote service = new MyRemoteImpl();
			Naming.rebind("Remote Hello", service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendAudio(Object o) {
		// TODO Auto-generated method stub
		
	}

}
