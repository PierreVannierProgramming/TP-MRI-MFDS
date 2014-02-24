package fr.istic.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Client {

	private static final String EXCHANGE_NAME = "chat";

	public static void main(String[] argv) throws Exception {
		
		String room = "chat.mri";
		String name = "Anonymous";
		
		if (argv.length > 1) {
			name = argv[0];
			room = argv[1];
		}
		if (argv.length == 1)
			name = argv[0];
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, room);

		Thread t = new Thread(new Afficheur(channel, queueName)); 
		t.start();
		
		System.out.println("Entering " + room + ". Welcome, " + name + "!");

		boolean b = true;
		
		while (b) {
			String str = lireMessageAuClavier();
			String msg = null;
			
			if (str != null) {
				msg = buildMessage(room, name, str);
				channel.basicPublish(EXCHANGE_NAME, room, null, msg.getBytes());
			}
		}
		
		channel.close();
		connection.close();
	}

	public static String lireMessageAuClavier() {
		BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
		String bf = null;
		try {
			bf = buff.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bf;
	}
	
	public static String buildMessage(String topic, String name, String message) {
		return topic + "#" + name + " > " + message;
	}
	
}