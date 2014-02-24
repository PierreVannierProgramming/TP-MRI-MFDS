package tp.rmi.serveur;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import tp.rmi.common.ChatRemote;
import tp.rmi.common.ReceiveCallback;

public class ChatRemoteImpl extends UnicastRemoteObject implements ChatRemote {

	private static final long serialVersionUID = 6483015500175724528L;
	
	private List<ReceiveCallback> callbacks;

	protected ChatRemoteImpl() throws RemoteException {
		super();
		callbacks = new ArrayList<ReceiveCallback>();
	}

	@Override
	public String echo(String name, String message) {
		return (name + " > " + message);
	}

	@Override
	public void send(String name, String message) throws RemoteException {
		String str = echo(name, message);
		for (ReceiveCallback call : callbacks) {
			call.newMessage(str);
		}		
	}

	@Override
	public void registerCallback(ReceiveCallback callback) throws RemoteException {
		callbacks.add(callback);
	}

}
