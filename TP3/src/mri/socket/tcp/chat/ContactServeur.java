package mri.socket.tcp.chat;

import java.io.BufferedReader;
import java.io.IOException;


public class ContactServeur implements Runnable { 

	private BufferedReader buf;
	
	ContactServeur(BufferedReader buf) { 
		this.buf = buf; 
	}
	
	@Override
	public void run() { 
		try {
			while(true){
				String tmp;

				tmp = ServeurTCP.recevoirMessage(buf);
				System.out.println(tmp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
