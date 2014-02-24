package fr.istic.date.topic;

import java.util.Date;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EnvoyerDate {

	private static final String EXCHANGE_NAME = "date_topic";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		boolean b = true;
		
		while (b) {
			String date = getDate();
			String dateGMT = getDateGMT();

			channel.basicPublish(EXCHANGE_NAME, "date.locale", null, date.getBytes());
			System.out.println(" Date : " + date);
			
			channel.basicPublish(EXCHANGE_NAME, "date.GMT", null, dateGMT.getBytes());
			System.out.println(" Date GMT : " + dateGMT);
			
			Thread.sleep(1000);
		}
		
		channel.close();
		connection.close();
	}

	private static String getDate(){
		return (new Date()).toString(); 
	}
	
	@SuppressWarnings("deprecation")
	private static String getDateGMT() { 
		return (new Date()).toGMTString(); 
	} 	
}