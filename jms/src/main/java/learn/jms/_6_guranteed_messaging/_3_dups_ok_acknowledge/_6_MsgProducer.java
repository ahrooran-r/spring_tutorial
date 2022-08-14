package learn.jms._6_guranteed_messaging._3_dups_ok_acknowledge;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _6_MsgProducer {
    public static void main(String[] args) throws NamingException {

        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             // dups ok acknowledgment mode selected
             // most efficient and fast performance way
             // but client should be able to handle duplicate messages
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin",
                     JMSContext.DUPS_OK_ACKNOWLEDGE);
        ) {
            jmsContext.createProducer().send(queue, "Hello World");
            System.out.println("Message sent!");
        }
    }
}
