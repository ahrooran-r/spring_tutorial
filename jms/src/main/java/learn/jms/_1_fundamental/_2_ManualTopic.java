package learn.jms._1_fundamental;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _2_ManualTopic {

    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext;
        ConnectionFactory connectionFactory;
        Connection connection;
        Session session;
        Topic topic;

        MessageProducer producer;
        MessageConsumer consumer1;
        MessageConsumer consumer2;
        MessageConsumer consumer3;

        // 1. create context
        initialContext = new InitialContext();
        // 2. create connection factory
        connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        // 3. create connection
        connection = connectionFactory.createConnection("admin", "admin");
        // 4. create session
        session = connection.createSession();

        // 5. create queue
        topic = (Topic) initialContext.lookup("topic/myTopic");

        // 6. create producer
        producer = session.createProducer(topic);

        // 7. create consumer
        // Note that consumers have to be created BEFORE sending a message
        // This is different from QUEUE
        consumer1 = session.createConsumer(topic);
        consumer2 = session.createConsumer(topic);
        consumer3 = session.createConsumer(topic);

        // 8. send message
        TextMessage message = session.createTextMessage("Hello World!");
        producer.send(message);
        System.out.println("Message successfully sent: " + message.getText());

        // 9. start the connection
        connection.start();

        // 10. receive message
        TextMessage receivedMessage1 = (TextMessage) consumer1.receive(5000);
        System.out.println("Message successfully received for consumer1: " + receivedMessage1.getText());

        TextMessage receivedMessage2 = (TextMessage) consumer2.receive();
        System.out.println("Message successfully received for consumer2: " + receivedMessage2.getText());

        TextMessage receivedMessage3 = (TextMessage) consumer3.receive();
        System.out.println("Message successfully received for consumer3: " + receivedMessage3.getText());

    }
}
