package learn.jms._5_filter_messages;

import learn.jms._5_filter_messages.util.Claim;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _1_ClaimManagement {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/claimQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin");
        ) {
            JMSProducer producer = jmsContext.createProducer();
            // using filters in consumer
            String filterQuery = "isOkay=true and JMSPriority between 5 and 9";
            JMSConsumer consumer = jmsContext.createConsumer(queue, filterQuery);

            // create object messages
            Claim claim_1 = new Claim(1, "barbossa", "Orthopedic", "PKPInsurance", 200000d);
            ObjectMessage claim_1_message = jmsContext.createObjectMessage(claim_1);
            claim_1_message.setBooleanProperty("isOkay", true);
            producer.setPriority(7);
            producer.send(queue, claim_1_message);
            System.out.println("Claim 1 sent");

            Claim claim_2 = new Claim(2, "hector", "surgeon", "PKPInsurance", 20000d);
            ObjectMessage claim_2_message = jmsContext.createObjectMessage(claim_2);
            claim_2_message.setBooleanProperty("isOkay", false);
            producer.setPriority(3);
            producer.send(queue, claim_2_message);
            System.out.println("Claim 2 sent");

            Claim receivedClaim = consumer.receiveBody(Claim.class);
            //ObjectMessage receivedClaim = (ObjectMessage) consumer.receive();
            System.out.println("Message received: " + receivedClaim + " and is equal to " + (receivedClaim.equals(claim_1) ? "claim_1" : "claim_2"));
            //System.out.println("Message received: " + receivedClaim.getObject() + " " + receivedClaim.getJMSPriority());
        }
    }
}
