package mri.socket.tcp.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;


public class ContactServeur implements Runnable { 

	private BufferedReader buf; 
	private boolean status = true;
	
	ContactServeur(BufferedReader buf) { 
		this.buf = buf; 
	}
	@Override
	public void run() { 
		while(true){
			String tmp;
			try {
				tmp = ServeurTCP.recevoirMessage(buf);
				System.out.println(tmp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void off() {
		status = false ;
	}
}
