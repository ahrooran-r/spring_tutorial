package learn.jms._1_fundamental;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _3_JMSQueue {
    public static void main(String[] args) throws NamingException {

        InitialContext initialContext;
        Queue queue;

        initialContext = new InitialContext();

        queue = (Queue) initialContext.lookup("queue/myQueue");

        // Can't use ConnectionFactory because it is not AutoCloseable:
        // Hence use ActiveMQConnectionFactory
        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin");
        ) {
            // with one line, we can send message
            jmsContext.createProducer().send(queue, "Hello World!");
            System.out.println("Message successfully sent");

            // with one line, we can receive message
            // directly receive body of the message -> impossible in old way
            String receivedMessage = jmsContext.createConsumer(queue).receiveBody(String.class);
            System.out.println("Message successfully received: " + receivedMessage);
        }
    }
}
