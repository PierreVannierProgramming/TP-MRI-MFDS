package tp.rmi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException; 
import java.rmi.NotBoundException; 
import java.rmi.RemoteException; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry; 

import tp.rmi.common.ChatRemote;
 
public class ChatClient { 

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException { 

		Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT); 
		System.out.println(registry); 
		ChatRemote chat = (ChatRemote) registry.lookup("chat"); 

		Afficheur aff = new Afficheur();
		chat.registerCallback(aff);
		
		String name = null;
		if (args.length == 0)
			name = "Anonymous";
		else
			name = args[0];
		
		String str = lireMessageAuClavier();
		
		while (!str.equals("fin")) {
			chat.send(name, str);
			str = lireMessageAuClavier();
		}
		
	}

	public static String lireMessageAuClavier() {
		BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		try {
			str = buff.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
} 
