package learn.jms._6_guranteed_messaging._2_client_acknowledge;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _3_MsgConsumer {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             // Client acknowledge mode selected
             // With this mode, the load on jms is reduced as it does not have wait for acknowledgement
             // It will do other things until a client acknowledgement is received
             // But client should acknowledge each message manually
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin",
                     JMSContext.CLIENT_ACKNOWLEDGE);
        ) {
            JMSConsumer consumer = jmsContext.createConsumer(queue);
            TextMessage receivedMessage = (TextMessage) consumer.receive();
            System.out.println("Message received: " + receivedMessage.getText());

            // If I comment out below line, then consumer will receive the message again and again
            receivedMessage.acknowledge();
        }
    }
}
