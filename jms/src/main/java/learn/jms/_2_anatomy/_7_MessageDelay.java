package learn.jms._2_anatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _7_MessageDelay {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin")
        ) {

            // setting delivery delay
            jmsContext.createProducer().setDeliveryDelay(2000).send(queue, "Hello World!");
            System.out.println("Message successfully sent");

            Message receivedMessage = jmsContext.createConsumer(queue).receive(2500);
            System.out.println("Message received: " + receivedMessage.getBody(String.class));
        }
    }
}
