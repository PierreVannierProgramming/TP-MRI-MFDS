package mri.socket.tcp.thread;

import java.net.Socket;

public class TraiteUnClient implements Runnable { 

	private Socket socketVersUnClient; 

	public TraiteUnClient(Socket socket) { 
		socketVersUnClient = socket; 
	} 

	@Override 
	public void run() { 
		ServeurTCP.traiterSocketCliente(socketVersUnClient);
	}
}
