package mri.socket.tcp.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP {
	
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(9999);

			while (true) {
				System.out.println("Serveur en attente.");
				Socket socketVersUnClient = null;

				socketVersUnClient = server.accept();
				Thread t = new Thread(new TraiteUnClient(socketVersUnClient)); 
				t.start(); 
				System.out.println("Le client " + socketVersUnClient.getInetAddress() + " est connecte.");

				//traiterSocketCliente(socketVersUnClient);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void traiterSocketCliente(Socket socketVersUnClient) {
		BufferedReader buf = null;
		PrintWriter wri = null;
		try {
			buf = creerReader(socketVersUnClient);
			wri = creerPrinter(socketVersUnClient);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		StringBuilder n = null;
		try {
			n = new StringBuilder(avoirNom(buf));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		n.append(" > ");

		String name = n.toString();

		String s = null;
		try {
			do {				
				s = recevoirMessage(buf);
				envoyerMessage(wri, name+s);
				//System.out.println("nom " + st);
			} while (s != null);
		} catch (IOException e) {
			try {
				socketVersUnClient.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
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
	
	public static String avoirNom(BufferedReader reader) throws IOException {
		String tmp = reader.readLine();
		String[] truc = new String[2];
		truc = tmp.split(" : ");
		return truc[1];
	}
}
