package learn.jms._9_spring_jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${jms.myQueue}")
    private String queue;

    public void send(String message) {
        jmsTemplate.convertAndSend(queue, message);
    }

}
