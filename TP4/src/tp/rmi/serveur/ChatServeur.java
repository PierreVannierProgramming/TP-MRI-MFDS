package tp.rmi.serveur;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;

import tp.rmi.common.ChatRemote;

public class ChatServeur {
	 
	public static void main(String[] args) throws RemoteException, MalformedURLException { 
		String pathToClasses = Paths.get("bin").toUri().toURL().toString(); 
		System.out.println(pathToClasses); 
		System.setProperty("java.rmi.server.codebase", pathToClasses); 
		System.setProperty("java.rmi.server.hostname", "148.60.3.100"); 

		ChatRemote chat = new ChatRemoteImpl(); 
		Naming.rebind("//localhost/chat", chat); 

	} 
}
