package learn.jms._7_message_grouping;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.IllegalStateException;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroupMessaging {
    public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

        Map<String, String> receivedMessages = new ConcurrentHashMap<>();

        InitialContext initialContext = new InitialContext();

        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext("admin", "admin");

             // this is for the consumer
             // if same jms context is used, there will be error
             JMSContext anotherJMSContext = connectionFactory.createContext("admin", "admin");
        ) {

            JMSProducer producer = jmsContext.createProducer();

            JMSConsumer consumer = anotherJMSContext.createConsumer(queue);
            consumer.setMessageListener(new SomeListener("Consumer-1", receivedMessages));

            JMSConsumer anotherConsumer = anotherJMSContext.createConsumer(queue);
            anotherConsumer.setMessageListener(new SomeListener("Consumer-2", receivedMessages));

            int count = 10;
            TextMessage[] messages = new TextMessage[count];
            for (int i = 0; i < count; i++) {
                messages[i] = jmsContext.createTextMessage("Group-0 Message: " + i);
                // set group id property
                messages[i].setStringProperty("JMSXGroupID", "Group-0");
                producer.send(queue, messages[i]);
            }

            Thread.sleep(2000);

            for (TextMessage message : messages) {
                if (!receivedMessages.get(message.getText()).equals("Consumer-1")) {
                    throw new IllegalStateException("Group message " + message.getText() + "has gone to wrong receiver");
                }
            }
        }
    }
}

@AllArgsConstructor
class SomeListener implements MessageListener {

    private final String name;
    private final Map<String, String> receivedMessages;

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        System.out.println("Message: " + textMessage.getText());
        System.out.println("Listener name: " + name);
        receivedMessages.put(textMessage.getText(), name);
    }
}