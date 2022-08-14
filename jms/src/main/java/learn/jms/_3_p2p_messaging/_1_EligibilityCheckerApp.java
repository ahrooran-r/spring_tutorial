package learn.jms._3_p2p_messaging;

import learn.jms._3_p2p_messaging.util.EligibilityCheckListener;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

// We can have multiple instances of this checker app to run in parallel
// so if 1000 messages are incoming and 5 instances are running parallel, then each instance would consume more or less 1000/5 messages
public class _1_EligibilityCheckerApp {
    public static void main(String[] args) throws NamingException, InterruptedException {

        InitialContext initialContext = new InitialContext();

        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try (
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = connectionFactory.createContext("admin", "admin")
        ) {
            JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            consumer.setMessageListener(new EligibilityCheckListener());

            Thread.sleep(10_000);
        }
    }
}
