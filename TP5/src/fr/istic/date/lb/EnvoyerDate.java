package fr.istic.date.lb;

import java.util.Date;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EnvoyerDate {

	private static final String EXCHANGE_NAME = "date";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		boolean b = true;
		
		while (b) {
			String message = getDate();

			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println(" Date : " + message);
			Thread.sleep(1000);
		}
		
		channel.close();
		connection.close();
	}

	private static String getDate(){
		return (new Date()).toString(); 
	}

	/*private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0) return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}*/
}

