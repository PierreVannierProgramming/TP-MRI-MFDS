package mri.socket.tcp.charset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP {
	static String charset;
	public static void main(String[] args) throws IOException {
		if (args.length < 2)
			charset = "UTF-8";
		else
			charset = args[1];
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(9999);
			while (true) {
				System.out.println("Serveur en attente.");
				Socket socketVersUnClient = null;
				socketVersUnClient = server.accept();
				System.out.println("Le client " + socketVersUnClient.getInetAddress() + " est connecte.");
				traiterSocketCliente(socketVersUnClient);
			}
		} catch (IOException e) {
					e.printStackTrace();
				}
		finally {
			server.close();
		}
	}
	
	public static void traiterSocketCliente(Socket socketVersUnClient) {
		BufferedReader buf = null;
		PrintWriter wri = null;
		try {
			buf = creerReader(socketVersUnClient, charset);
			wri = creerPrinter(socketVersUnClient, charset);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		StringBuilder n = null;
		try {
			n = new StringBuilder(avoirNom(buf));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		n.append("> ");
		String name = n.toString();

		String s = null;
		try {
			do {


				s = recevoirMessage(buf);
				envoyerMessage(wri, name+s);

			} while (s != null);
		} catch (IOException e) {
			try {
				socketVersUnClient.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
		
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
	
	public static String avoirNom(BufferedReader reader) throws IOException {
		String tmp = reader.readLine();
		String[] truc;
		if(tmp!=null){
			truc = tmp.split(" : ");
			if(truc.length >=2){
				return truc[1];
				}
			}
		return "unknown";
	}
}
