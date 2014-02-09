package mri.socket.tcp.nom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTCP {

	public static void main(String[] args) {
		Socket client = null;
		
		try {
			client = new Socket(InetAddress.getLocalHost(), 9999);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader buf = null;
		PrintWriter wri = null;
		try {
			buf = creerReader(client);
			wri = creerPrinter(client);
			
			String name = null;
			if (args.length == 0)
				name = "noname";
			else
				name = args[0];
			
			envoyerNom(wri, name);
			//String tmp = recevoirMessage(buf);
			//System.out.println("recevoirMessage " + tmp);
			
			String str = lireMessageAuClavier();
			//System.out.println("lireMessageAuClavier " + str);
			while (!str.equals("fin")) {
				//System.out.println("Envoyer message " + str);
				envoyerMessage(wri, str);
				String tmp = recevoirMessage(buf);
				System.out.println(tmp);
				if (!tmp.split(">")[0].equals(name)){
				System.out.println("Erreur : le serveur m'a envoyer un message avec un non different du mien.");
				}
				str = lireMessageAuClavier();
			}
			client.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static String lireMessageAuClavier() throws IOException{
		BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
		return buff.readLine();
	}
	
	public static BufferedReader creerReader(Socket socketVersUnClient) throws IOException {
		return new BufferedReader(new InputStreamReader(socketVersUnClient.getInputStream()));
	}
	
	public static PrintWriter creerPrinter(Socket socketVersUnClient) throws IOException {
		return new PrintWriter(socketVersUnClient.getOutputStream(), true);
	}
	
	public static String recevoirMessage(BufferedReader reader) throws IOException {
		String s = reader.readLine();
		return s;
	}
	
	public static void envoyerMessage(PrintWriter printer, String message) throws IOException {
		printer.println(message);
	}
	
	public static void envoyerNom(PrintWriter printer, String nom) throws IOException {
		printer.println("NAME : " + nom);
	}

}
