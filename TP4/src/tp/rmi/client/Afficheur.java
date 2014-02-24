package tp.rmi.client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tp.rmi.common.ReceiveCallback;

public class Afficheur extends UnicastRemoteObject implements ReceiveCallback, Serializable {

	protected Afficheur() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1764863874085768980L;

	@Override
	public void newMessage(String message) throws RemoteException {
		System.out.println(message);
		
	}

}
