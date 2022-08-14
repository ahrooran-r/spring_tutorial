package learn.jms._1_fundamental;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;

public class _4_ManualQueueBrowser {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext;
        ConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Queue queue;

        MessageProducer producer;
        MessageConsumer consumer;
        QueueBrowser browser;

        initialContext = new InitialContext();

        connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        connection = connectionFactory.createConnection("admin", "admin");

        session = connection.createSession();

        queue = (Queue) initialContext.lookup("queue/myQueue");

        producer = session.createProducer(queue);
        consumer = session.createConsumer(queue);
        browser = session.createBrowser(queue);

        for (int i = 0; i < 5; i++) {
            TextMessage message = session.createTextMessage("Message " + i);
            producer.send(message);
        }
        System.out.println("Messages successfully sent");

        // Enumerating browser
        Enumeration e = browser.getEnumeration();
        while (e.hasMoreElements()) {
            TextMessage browsedMessage = (TextMessage) e.nextElement();
            System.out.println("Currently browsing: " + browsedMessage.getText());
        }

        connection.start();

        // Here is actually receiving message
        for (int i = 0; i < 5; i++) {
            TextMessage receivedMessage = (TextMessage) consumer.receive(1000);
            System.out.println("Message successfully received: " + receivedMessage.getText());
        }
    }
}
