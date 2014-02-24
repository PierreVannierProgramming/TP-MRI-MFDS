package mri.socket.udp.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatMulticast {
	static String ip = "225.0.4.5"; 
	static int port = 9999;

	public static void main(String[] args) throws IOException { 
		InetAddress groupeIP = InetAddress.getByName(ip); 
		
		MulticastSocket socket = new MulticastSocket(port); 
		socket.joinGroup(groupeIP);
		
		String nam = null;
		if (args.length == 0)
			nam = "Anonymous";
		else
			nam = args[0];
		String name = nam + " > ";
		
		Thread recept = new Thread(new ContactServeur(socket));
		recept.start();
		
		String msg = null;
		try {
			while (true) { 
				msg = lireMessageAuClavier();
				if (!msg.equals(null))
					envoyerMessage(socket, msg, name);
			}
		} catch (Exception e) { }
		finally {
			socket.close();
		}
	} 

	
	public static void envoyerMessage(MulticastSocket s, String message, String nom) throws IOException {
		String messageComplet = nom + message;
		
		byte[] contenuMessage = messageComplet.getBytes(); 
		
		DatagramPacket msg = new DatagramPacket(contenuMessage, contenuMessage.length, InetAddress.getByName(ip), port); 
		
		s.send(msg);

	}
	
	public static String recevoirMessage(MulticastSocket s) throws IOException {
		byte[] contenuMessage = new byte[1024]; 
		DatagramPacket message = new DatagramPacket(contenuMessage, contenuMessage.length); 
		s.receive(message); 
		return new String(contenuMessage); 
	}
	
	public static String lireMessageAuClavier() throws IOException{
		BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
		String b = buff.readLine();
		return b;
	}
}
