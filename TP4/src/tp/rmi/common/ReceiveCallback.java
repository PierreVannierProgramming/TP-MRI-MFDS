package tp.rmi.common;

import java.rmi.Remote; 
import java.rmi.RemoteException; 
 
public interface ReceiveCallback extends Remote{ 
	
  public void newMessage(String message) throws RemoteException; 
} 
