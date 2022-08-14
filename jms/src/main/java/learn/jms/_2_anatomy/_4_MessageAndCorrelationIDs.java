package learn.jms._2_anatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _4_MessageAndCorrelationIDs {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext;
        Queue requestQueue;
        Queue responseQueue;

        initialContext = new InitialContext();

        requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin");
        ) {

            responseQueue = jmsContext.createTemporaryQueue();

            // producer is sending to requestQueue
            JMSProducer requestProducer = jmsContext.createProducer();
            requestProducer.setJMSReplyTo(responseQueue);

            for (int i = 0; i < 5; i++) {
                Message message = jmsContext.createTextMessage("Message-" + i);
                requestProducer.send(requestQueue, message);
            }
            System.out.println("All messages successfully sent to requestQueue");

            // consumer is receiving from requestQueue
            JMSConsumer requestConsumer = jmsContext.createConsumer(requestQueue);

            // Now I create another producer to send replies back to to responseQueue
            JMSProducer responseProducer = jmsContext.createProducer();

            for (int i = 0; i < 5; i++) {
                TextMessage receivedMessage = (TextMessage) requestConsumer.receive(100);
                System.out.println("MessageID: -> " + receivedMessage.getJMSMessageID() + " received from requestQueue: " + receivedMessage.getText() + ". Sending reply to: " + receivedMessage.getJMSReplyTo());

                // changing receivedMessage to responseMessage
                receivedMessage.setJMSCorrelationID(receivedMessage.getJMSMessageID());
                receivedMessage.setJMSReplyTo(responseQueue);

                // sending it to responseQueue
                responseProducer.send(receivedMessage.getJMSReplyTo(), receivedMessage);
            }

            System.out.println("All messages successfully sent to responseQueue");

            // another consumer is receiving from responseQueue -> for verification purpose
            JMSConsumer responseConsumer = jmsContext.createConsumer(responseQueue);

            for (int i = 0; i < 5; i++) {
                TextMessage receivedMessage = (TextMessage) responseConsumer.receive(1000);
                System.out.println("MessageID: -> " + receivedMessage.getJMSMessageID() + " CorrelationID: " + receivedMessage.getJMSCorrelationID() + " received from responseQueue: " + receivedMessage.getText());
            }
        }
    }
}
