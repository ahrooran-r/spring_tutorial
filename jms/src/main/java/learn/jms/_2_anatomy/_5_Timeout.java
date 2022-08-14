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

public class _5_Timeout {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext;
        Queue queue;

        initialContext = new InitialContext();

        queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin");
        ) {
            // setting time to live
            jmsContext.createProducer().setTimeToLive(2000).send(queue, "Hello World!");
            System.out.println("Message successfully sent");

            // wait for 3 seconds so message can expire
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(3000));

            Message receivedMessage = jmsContext.createConsumer(queue).receive(5000);
            System.out.println("Message successfully received: " + receivedMessage.getBody(String.class));
        }
    }
}
