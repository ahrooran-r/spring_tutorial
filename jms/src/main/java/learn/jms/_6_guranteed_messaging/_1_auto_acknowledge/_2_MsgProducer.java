package learn.jms._6_guranteed_messaging._1_auto_acknowledge;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _2_MsgProducer {
    public static void main(String[] args) throws NamingException {

        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             // Auto acknowledgment mode selected
             // It ensures message is delivered only once from sender to consumer
             // Most time consuming and less performance way, but reliable
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin",
                     JMSContext.AUTO_ACKNOWLEDGE);
        ) {
            jmsContext.createProducer().send(queue, "Hello World");
            System.out.println("Message sent!");
        }
    }
}
