package learn.jms._4_pub_sub_messaging;

import learn.jms._4_pub_sub_messaging.util.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _3_WellnessApp {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();
        Topic empTopic = (Topic) initialContext.lookup("topic/empTopic");

        try (
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = connectionFactory.createContext("admin", "admin")
        ) {

            JMSConsumer consumer_1 = jmsContext.createSharedConsumer(empTopic, "subscription_2");
            JMSConsumer consumer_2 = jmsContext.createSharedConsumer(empTopic, "subscription_2");

            for (int i = 1; i <= 10; i += 2) {
                Employee receivedEmployee = consumer_1.receiveBody(Employee.class);
                System.out.println("WellnessApp consumer 1 printing: " + receivedEmployee.getId());

                receivedEmployee = consumer_2.receiveBody(Employee.class);
                System.out.println("WellnessApp consumer 2 printing: " + receivedEmployee.getPhone());
            }
        }
    }
}
