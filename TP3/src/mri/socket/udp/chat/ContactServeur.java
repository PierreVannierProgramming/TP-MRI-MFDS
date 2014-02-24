package mri.socket.udp.chat;

import java.io.IOException;
import java.net.MulticastSocket;


public class ContactServeur implements Runnable { 

	private MulticastSocket sock;
	
	ContactServeur(MulticastSocket s) { 
		this.sock = s; 
	}
	
	@Override
	public void run() { 
		while(true){
			String tmp;
			try {
				tmp = ChatMulticast.recevoirMessage(sock);
				System.out.println(tmp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
