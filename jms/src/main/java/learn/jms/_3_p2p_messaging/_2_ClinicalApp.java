package learn.jms._3_p2p_messaging;

import learn.jms._3_p2p_messaging.util.Patient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class _2_ClinicalApp {
    public static void main(String[] args) throws NamingException {

        InitialContext initialContext = new InitialContext();

        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try (
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = connectionFactory.createContext("admin", "admin")
        ) {
            // create message
            ObjectMessage message = jmsContext.createObjectMessage(new Patient(1, "barbossa", "blue_cross_insurance", 123.45d, 20_000d));

            // send message
            JMSProducer producer = jmsContext.createProducer();
            producer.send(requestQueue, message);
            System.out.println("Message sent!");
        }
    }
}
