package learn.jms._1_fundamental;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _1_ManualQueue {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext;
        ConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Queue queue;

        MessageProducer producer;
        MessageConsumer consumer;

        // 1. create context
        initialContext = new InitialContext();
        // 2. create connection factory
        connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        // 3. create connection
        connection = connectionFactory.createConnection("admin", "admin");
        // 4. create session
        session = connection.createSession();

        // 5. create queue
        queue = (Queue) initialContext.lookup("queue/myQueue");

        // 6. create producer
        producer = session.createProducer(queue);
        // 7. send message
        TextMessage message = session.createTextMessage("Hello World!");
        producer.send(message);
        System.out.println("Message successfully sent: " + message.getText());

        // 8. create consumer
        consumer = session.createConsumer(queue);

        // 9. start the connection
        connection.start();
        // 10. receive message
        TextMessage receivedMessage = (TextMessage) consumer.receive(5000);
        System.out.println("Message successfully received: " + receivedMessage.getText());

    }
}
