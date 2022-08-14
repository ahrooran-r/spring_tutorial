package learn.jms._2_anatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class _6_AccessExpiredMessages {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");
        Queue expiryQueue = (Queue) initialContext.lookup("queue/expiryQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin");
        ) {
            // setting time to live
            jmsContext.createProducer().setTimeToLive(1000).send(queue, "Hello World!");
            System.out.println("Message successfully sent");

            // wait for 3 seconds so message can expire
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(2000));

            try {
                Message receivedMessage = jmsContext.createConsumer(queue).receive(2500);
                System.out.println("Message received: " + receivedMessage.getBody(String.class));

            } catch (NullPointerException nullPointerException) {

                System.out.println("Message moved to ExpiryQueue");

                Message receivedMessage = jmsContext.createConsumer(expiryQueue).receive(100);
                System.out.println("Message retrieved from ExpiryQueue: " + receivedMessage.getBody(String.class));
            }
        }
    }
}
