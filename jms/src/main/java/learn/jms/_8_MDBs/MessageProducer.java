package learn.jms._8_MDBs;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
@LocalBean
public class MessageProducer {

    @Resource(mappedName = "jms/queue/FromSellSide")
    Queue queue;

    @Inject
    JMSContext jmsContext;

    public void sendMessage(String message) {
        jmsContext.createProducer().send(queue, message);
    }
}
