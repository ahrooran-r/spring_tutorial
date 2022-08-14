package learn.jms._4_pub_sub_messaging;

import learn.jms._4_pub_sub_messaging.util.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _4_HRApp {
    public static void main(String[] args) throws NamingException {

        InitialContext initialContext = new InitialContext();
        Topic empTopic = (Topic) initialContext.lookup("topic/empTopic");

        try (
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = connectionFactory.createContext("admin", "admin")
        ) {

            JMSProducer producer = jmsContext.createProducer();

            for (int i = 1; i <= 10; i++) {
                Employee employee = new Employee(i, "Barbossa", "Hector", "bar@hec.com", "teacher", String.valueOf(i * 12345));
                producer.send(empTopic, employee);
            }
            System.out.println("Messages sent!");
        }
    }
}
