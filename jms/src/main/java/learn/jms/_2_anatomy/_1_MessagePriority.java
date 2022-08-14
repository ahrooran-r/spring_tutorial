package learn.jms._2_anatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _1_MessagePriority {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = connectionFactory.createContext("admin", "admin");
        ) {

            JMSProducer producer = jmsContext.createProducer();

            for (int i = 0; i < 10; i++) {
                Message message = jmsContext.createTextMessage("Message: " + i);

                // set priority for producer before sending message
                producer.setPriority(i);
                producer.send(queue, message);
            }

            JMSConsumer consumer = jmsContext.createConsumer(queue);
            for (int i = 0; i < 10; i++) {
                Message receivedMessage = consumer.receive(1000);
                System.out.println("Message received: " + receivedMessage.getBody(String.class) + " Priority: " + receivedMessage.getJMSPriority());
            }
        }
    }
}
