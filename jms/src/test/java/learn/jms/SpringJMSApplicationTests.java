package learn.jms;

import learn.jms._9_spring_jms.MessageSender;
import learn.jms._9_spring_jms.SpringJMSApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringJMSApplication.class)
public class SpringJMSApplicationTests {

    @Autowired
    MessageSender sender;

    @Test
    public void testSendAndReceive() {
        sender.send("Hello Spring JMS!");
    }

}
