package learn.jms._4_pub_sub_messaging;

import learn.jms._4_pub_sub_messaging.util.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _1_SecurityApp_DurableConsumer {
    public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

        InitialContext initialContext = new InitialContext();
        Topic empTopic = (Topic) initialContext.lookup("topic/empTopic");

        try (
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = connectionFactory.createContext("admin", "admin")
        ) {

            // set a client id -> IMPORTANT
            jmsContext.setClientID("SecurityApp");

            // set a name to consumer -> IMPORTANT
            JMSConsumer consumer = jmsContext.createDurableConsumer(empTopic, "subscription_1");
            consumer.close();

            Thread.sleep(10_000);

            consumer = jmsContext.createDurableConsumer(empTopic, "subscription_1");

            // when I create a durable consumer, jms remembers the name `subscription_1`
            // now if the consumer is shut down (using #close method), and reconnected (see above 2 lines)
            // if there are any messages sent to topic within this time period, those messages will be sent to this
            // consumer (since the name is same as `subscription_1`) once it comes back online

            Employee receivedEmployee = consumer.receiveBody(Employee.class);
            System.out.println("SecurityApp printing: " + receivedEmployee.getFirstName() + " " + receivedEmployee.getLastName());

            consumer.close();
            jmsContext.unsubscribe("subscription_1");

            // once the name is unsubscribed, then jms will no longer retain messages for this consumer
        }
    }
}
