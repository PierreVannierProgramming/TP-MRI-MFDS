package mri.socket.address.echo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class AfficherInterface {	
	public static void main(String[] args) {
		Enumeration<NetworkInterface> inter = null;
		try {
			inter = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		while (inter.hasMoreElements()) {
			NetworkInterface net = inter.nextElement();
			Enumeration<InetAddress> addr = net.getInetAddresses();
			
			System.out.println(net.getName() + ": " + net.getDisplayName());
			
			while (addr.hasMoreElements()) {
				InetAddress tmp = addr.nextElement();
				System.out.println("-> " + tmp.getHostAddress());
			}
		}
	}
}
