package learn.springframework.configuration.annotation;

import org.springframework.stereotype.Component;

@Component
public class SadFortuneService implements FortuneService {

    @Override
    public String getFortune() {
        return "Sad day to you!";
    }
}
