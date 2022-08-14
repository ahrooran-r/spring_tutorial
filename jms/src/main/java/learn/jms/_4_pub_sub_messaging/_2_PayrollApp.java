package learn.jms._4_pub_sub_messaging;

import learn.jms._4_pub_sub_messaging.util.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _2_PayrollApp {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();
        Topic empTopic = (Topic) initialContext.lookup("topic/empTopic");

        try (
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = connectionFactory.createContext("admin", "admin")
        ) {

            JMSConsumer consumer = jmsContext.createConsumer(empTopic);
            Employee receivedEmployee = consumer.receiveBody(Employee.class);

            System.out.println("SecurityApp printing: " + receivedEmployee.getEmail() + " " + receivedEmployee.getPhone());
        }
    }
}
