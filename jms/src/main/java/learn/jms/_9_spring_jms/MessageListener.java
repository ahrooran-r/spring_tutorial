package learn.jms._9_spring_jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @JmsListener(destination = "${jms.myQueue}")
    public void receive(String message) {
        System.out.println("Message received: " + message);
    }

}
