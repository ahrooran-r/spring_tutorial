package learn.springframework.configuration.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("myTennisCoach")
public class TennisCoach implements Coach {

    private FortuneService fortuneService;

    // @Autowired -> use to do component autowiring
    @Autowired
    public TennisCoach(@Qualifier("happyFortuneService") FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Practise tennis!";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }
}

//@Component
//public class TennisCoach implements Coach {
//    @Override
//    public String getDailyWorkout() {
//        return "Practise tennis!";
//    }
//}

/*
 * @component -> without explicit name will generate its own id like this: 'tennisCoach'
 * */
