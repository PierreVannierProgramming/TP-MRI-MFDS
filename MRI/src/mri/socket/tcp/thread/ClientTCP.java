package mri.socket.tcp.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader buf = null;
		PrintWriter wri = null;
		try {			
			String name = null, charset = null;
			if (args.length == 0)
				name = "noname";
			else
				name = args[0];
			if (args.length < 2)
				charset = "UTF-8";
			else
				charset = args[1];
			
			buf = creerReader(client, charset);
			wri = creerPrinter(client, charset);
			envoyerNom(wri, name);
			String str = lireMessageAuClavier();
			while (!str.equals("fin")) {
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
			e1.printStackTrace();
		}
	}
	
	public static String lireMessageAuClavier() throws IOException{
		BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
		return buff.readLine();
	}
	
	public static BufferedReader creerReader(Socket socketVersUnClient, String charset) throws IOException {
		return new BufferedReader(new InputStreamReader(socketVersUnClient.getInputStream(),charset));
	}
	
	public static PrintWriter creerPrinter(Socket socketVersUnClient, String charset) throws IOException {
		return new PrintWriter(new OutputStreamWriter(socketVersUnClient.getOutputStream(),charset), true);
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
