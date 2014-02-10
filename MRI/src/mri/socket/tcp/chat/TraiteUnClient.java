package mri.socket.tcp.chat;

import java.net.Socket;


public class TraiteUnClient implements Runnable { 

	private Socket socketVersUnClient; 
	
	TraiteUnClient(Socket socket) { 
		socketVersUnClient = socket; 
	}
	
	@Override 
	public void run() { 
		ServeurTCP.traiterSocketCliente(socketVersUnClient);
	}
}
