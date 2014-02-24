package fr.istic.chat;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Afficheur implements Runnable {

	private Channel channel;
	private String queueName;
	
	public Afficheur(Channel c, String s) {
		this.channel = c;
		this.queueName = s;
	}
	
	public void run() {
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		try {
			channel.basicConsume(queueName, true, consumer);

			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();

				String message = new String(delivery.getBody());
				System.out.println(message);
			}
		} catch (Exception e) { e.printStackTrace(); }
	}


}
