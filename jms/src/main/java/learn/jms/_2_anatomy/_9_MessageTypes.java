package learn.jms._2_anatomy;

import learn.jms._2_anatomy.util.School;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class _9_MessageTypes {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin")
        ) {

            TextMessage textMessage = jmsContext.createTextMessage("Hello World!");
            ObjectMessage objectMessage = jmsContext.createObjectMessage(new School("JHC", 1000, (short) 30));

            BytesMessage bytesMessage = jmsContext.createBytesMessage();
            bytesMessage.writeUTF("HelloWorld in UTF encoding");
            bytesMessage.writeLong(System.nanoTime());

            StreamMessage streamMessage = jmsContext.createStreamMessage();
            streamMessage.writeBoolean(true);
            streamMessage.writeFloat(2.5F);

            MapMessage mapMessage = jmsContext.createMapMessage();
            mapMessage.setInt("square", 4);
            mapMessage.setDouble("PI", Math.PI);

            JMSProducer producer = jmsContext.createProducer();
            producer.send(queue, textMessage);
            producer.send(queue, objectMessage);
            producer.send(queue, bytesMessage);
            producer.send(queue, streamMessage);
            producer.send(queue, mapMessage);
            System.out.println("Messages successfully sent");

            JMSConsumer consumer = jmsContext.createConsumer(queue);

            TextMessage receiveTextMessage = (TextMessage) consumer.receive(100);
            System.out.println("Received text message: " + receiveTextMessage.getText());

            ObjectMessage receiveObjectMessage = (ObjectMessage) consumer.receive(100);
            System.out.println("Received object message: " + receiveObjectMessage.getObject().toString());

            BytesMessage receiveBytesMessage = (BytesMessage) consumer.receive(100);
            System.out.println("Received bytes message: UTF -> " + receiveBytesMessage.readUTF() + " Long -> " + receiveBytesMessage.readLong());

            StreamMessage receiveStreamMessage = (StreamMessage) consumer.receive(100);
            System.out.println("Received stream message: boolean -> " + receiveStreamMessage.readBoolean() + " float -> " + receiveStreamMessage.readFloat());

            MapMessage receiveMapMessage = (MapMessage) consumer.receive(100);
            System.out.println("Received map message: square -> " + receiveMapMessage.getInt("square") + " PI -> " + receiveMapMessage.getDouble("PI"));
        }
    }
}
