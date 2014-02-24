package tp.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatRemote extends Remote {
	public String echo(String name, String message) throws RemoteException;

	public void send(String name, String message) throws RemoteException; 

	public void registerCallback(ReceiveCallback callback) throws RemoteException; 
}
