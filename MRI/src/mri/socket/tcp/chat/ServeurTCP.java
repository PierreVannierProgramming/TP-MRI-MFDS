package mri.socket.tcp.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServeurTCP {
	private static   List<PrintWriter> printerSocketActives = new ArrayList<PrintWriter>();
	static final int nbThreads = 2 ;  
	static String charset;
	public static void main(String[] args) throws IOException {
		if (args.length < 2)
			charset = "UTF-8";
		else
			charset = args[1];
		ServerSocket server = null;
		try {
			server = new ServerSocket(9999);
			ExecutorService service = Executors.newFixedThreadPool(nbThreads);
			while (true) {
				System.out.println("Serveur en attente.");
				Socket socketVersUnClient = null;
				socketVersUnClient = server.accept();
				service.execute(new TraiteUnClient(socketVersUnClient));
				System.out.println("Le client " + socketVersUnClient.getInetAddress() + " est connecté.");
				//traiterSocketCliente(socketVersUnClient);
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
			ajouterPrinterSocketActives(wri);
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

		do {
			try {
				s = recevoirMessage(buf);
				envoyerATouteLesSocketsActive(s);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		} while (s != null);
		
		try {
			enleverPrinterSocketActives(wri);
			socketVersUnClient.close();
		} catch (IOException e) {
			e.printStackTrace();
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
	public static synchronized void envoyerATouteLesSocketsActive(String message) throws IOException {
		for (PrintWriter pwa : printerSocketActives) {
			envoyerMessage(pwa, message);
		}
	}
	
	public static synchronized void ajouterPrinterSocketActives(PrintWriter printer) {
		printerSocketActives.add(printer);
		}
	
	public static synchronized void enleverPrinterSocketActives(PrintWriter printer) {
		printerSocketActives.remove(printer);
		}
}
