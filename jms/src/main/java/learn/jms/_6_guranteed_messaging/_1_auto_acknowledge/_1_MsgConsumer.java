package learn.jms._6_guranteed_messaging._1_auto_acknowledge;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _1_MsgConsumer {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin");
        ) {
            String receivedMessage = jmsContext.createConsumer(queue).receiveBody(String.class);
            System.out.println(receivedMessage);
        }
    }
}
