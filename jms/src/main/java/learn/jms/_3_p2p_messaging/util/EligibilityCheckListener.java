package learn.jms._3_p2p_messaging.util;

import lombok.SneakyThrows;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;


public class EligibilityCheckListener implements MessageListener {

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {

            Patient receivedPatient = (Patient) ((ObjectMessage) message).getObject();
            System.out.println("Received the message: " + receivedPatient);

            boolean eligibilityCheckerResponse = executeSomeBusinessLogic(receivedPatient);

            InitialContext initialContext = new InitialContext();
            Queue responseQueue = (Queue) initialContext.lookup("queue/responseQueue");

            try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                 JMSContext jmsContext = connectionFactory.createContext("admin", "admin")
            ) {
                JMSProducer producer = jmsContext.createProducer();

                // place response onto another queue
                TextMessage response = jmsContext.createTextMessage(String.valueOf(eligibilityCheckerResponse));
                response.setJMSTimestamp(1000);
                producer.send(responseQueue, response);
                System.out.println("Response sent!");
            }
        }
    }

    private boolean executeSomeBusinessLogic(Patient patient) {
        return patient.getAmountToBePaid() >= 19_000;
    }
}
