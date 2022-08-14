package learn.jms._6_guranteed_messaging._4_transactions;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _7_MsgConsumer {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin",
                     JMSContext.SESSION_TRANSACTED);
        ) {
            JMSConsumer consumer = jmsContext.createConsumer(queue);
            String receivedMessage1 = consumer.receiveBody(String.class);
            System.out.println(receivedMessage1);
            jmsContext.commit();
            // since first message is committed, it will not be redelivered

            String receivedMessage2 = consumer.receiveBody(String.class);
            System.out.println(receivedMessage2);
            // however this message will be redelivered since it is not committed
            // so if you reran consumer for second time, Message 2 will be redelivered

            // BUT if you re ran for 3rd time, no messages will be redelivered because that
            // Message 2 would be committed at line 24 because now it is the FIRST message!
        }
    }
}
