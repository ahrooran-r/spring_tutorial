package learn.springframework.configuration.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CricketCoach implements Coach {

    // @Autowired -> used directly in fields
    // @Qualifier -> used when multiple bean alternatives are found
    //            -> in this case HappyFortuneService, SadFortuneService
    @Autowired
    @Qualifier("happyFortuneService")
    private FortuneService fortuneService;

    @Override
    public String getDailyWorkout() {
        return "Practise Cricket!";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }
}
