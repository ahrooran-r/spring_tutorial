package learn.jms._2_anatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _8_CustomMessageProperties {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin")
        ) {

            TextMessage message = jmsContext.createTextMessage("Hello World!");

            // setting custom properties -> many properties can be set for each data type
            message.setBooleanProperty("isStringMessage", true);
            message.setDoubleProperty("valOfPi", Math.PI);
            message.setIntProperty("sidesOfSquare", 4);
            message.setLongProperty("currentTimeMillis", System.currentTimeMillis());

            jmsContext.createProducer().send(queue, message);
            System.out.println("Message successfully sent");

            Message receivedMessage = jmsContext.createConsumer(queue).receive(100);
            System.out.println("Message received: " + receivedMessage.getBody(String.class));
            System.out.println("Property: isStringMessage -> " + receivedMessage.getBooleanProperty("isStringMessage"));
            System.out.println("Property: valOfPi -> " + receivedMessage.getDoubleProperty("valOfPi"));
            System.out.println("Property: sidesOfSquare -> " + receivedMessage.getIntProperty("sidesOfSquare"));
            System.out.println("Property: currentTimeMillis -> " + receivedMessage.getLongProperty("currentTimeMillis"));
        }
    }
}
