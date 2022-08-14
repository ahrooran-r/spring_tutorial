package learn.jms._2_anatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _3_DynamicRequestReply {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext;
        Queue requestQueue;
        Queue responseQueue;

        initialContext = new InitialContext();

        requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
        // rather than creating responseQueue like this, I can create temporary queues dynamically
        //responseQueue = (Queue) initialContext.lookup("queue/responseQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin");
        ) {

            // I'm creating temporary queue dynamically
            responseQueue = jmsContext.createTemporaryQueue();

            // producer is sending to requestQueue
            JMSProducer requestProducer = jmsContext.createProducer();

            Message message = jmsContext.createTextMessage("Placed the request!");
            // adding automatic reply destination
            message.setJMSReplyTo(responseQueue);
            requestProducer.send(requestQueue, message);
            System.out.println("Message successfully sent to requestQueue");

            // consumer is receiving from requestQueue
            JMSConsumer requestConsumer = jmsContext.createConsumer(requestQueue);
            TextMessage receivedMessage = (TextMessage) requestConsumer.receive(100);
            System.out.println("Message successfully received from requestQueue: " + receivedMessage.getText() + ". Sending reply to: " + receivedMessage.getJMSReplyTo());

            // Now I create another producer to send replies back to to responseQueue
            // But no need to HARD-CODE responseQueue here anymore
            JMSProducer producer = jmsContext.createProducer();
            producer.send(receivedMessage.getJMSReplyTo(), "Message received from requestQueue is: " + receivedMessage.getText());
            System.out.println("Message successfully sent to " + receivedMessage.getJMSReplyTo());

            // another consumer is receiving from responseQueue -> for verification purpose
            JMSConsumer responseConsumer = jmsContext.createConsumer(responseQueue);
            System.out.println("Message successfully received from responseQueue: " + responseConsumer.receiveBody(String.class));
        }
    }
}
