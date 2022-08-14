package learn.jms._2_anatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _2_ManualRequestReply {
    public static void main(String[] args) throws NamingException {

        InitialContext initialContext;
        Queue requestQueue;
        Queue responseQueue;

        initialContext = new InitialContext();

        requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
        responseQueue = (Queue) initialContext.lookup("queue/responseQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin");
        ) {

            // producer is sending to requestQueue
            JMSProducer requestProducer = jmsContext.createProducer();
            requestProducer.send(requestQueue, "Placed the request!");
            System.out.println("Message successfully sent to requestQueue");

            // consumer is receiving from requestQueue
            JMSConsumer requestConsumer = jmsContext.createConsumer(requestQueue);
            String receivedMessage = requestConsumer.receiveBody(String.class);
            System.out.println("Message successfully received from requestQueue: " + receivedMessage);

            // Now I create another producer to send replies back to to responseQueue
            JMSProducer producer = jmsContext.createProducer();
            producer.send(responseQueue, "Message received from requestQueue is: " + receivedMessage);
            System.out.println("Message successfully sent to responseQueue");

            // another consumer is receiving from responseQueue -> verifying it
            JMSConsumer responseConsumer = jmsContext.createConsumer(responseQueue);
            receivedMessage = responseConsumer.receiveBody(String.class);
            System.out.println("Message successfully received from responseQueue: " + receivedMessage);
        }
    }
}
