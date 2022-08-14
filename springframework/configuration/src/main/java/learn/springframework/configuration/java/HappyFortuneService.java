package learn.springframework.configuration.java;

import org.springframework.stereotype.Component;

@Component
public class HappyFortuneService implements FortuneService {

    @Override
    public String getFortune() {
        return "Happy day to you!";
    }
}
