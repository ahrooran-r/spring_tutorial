package learn.jms._6_guranteed_messaging._4_transactions;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _8_MsgProducer {
    public static void main(String[] args) throws NamingException, InterruptedException {

        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             // transaction
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin",
                     JMSContext.SESSION_TRANSACTED);
        ) {
            JMSProducer producer = jmsContext.createProducer();
            producer.send(queue, "Message 1");
            producer.send(queue, "Message 2");
            System.out.println("Messages appear as sent. But actually not.");

            Thread.sleep(5000);

            // only when I call commit, the messages would be transacted
            jmsContext.commit();
            System.out.println("Only now, the messages are sent to consumer");

            producer.send(queue, "Message 3");
            jmsContext.rollback();
            System.out.println("Message 3 will not be delivered");
        }
    }
}
