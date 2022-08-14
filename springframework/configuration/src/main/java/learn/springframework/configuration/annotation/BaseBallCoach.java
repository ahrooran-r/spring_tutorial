package learn.springframework.configuration.annotation;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
 * @Scope("singleton") -> set the BaseBallClass to have single object
 * */
@Component
@Scope("singleton")
public class BaseBallCoach implements Coach {

    private FortuneService fortuneService;
    private FortuneService someCrazyStuff;

    // @Autowired -> use to do setter autowiring
    @Autowired
    @Qualifier("happyFortuneService")
    public void setFortuneService(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    // can do autowired not only for setter methods but also any method
    @Autowired
    public void doSomeCrazyStuff(@Qualifier("sadFortuneService") FortuneService someCrazyStuff) {
        this.someCrazyStuff = someCrazyStuff;
    }

    @Override
    public String getDailyWorkout() {
        return "Practise Base ball!";
    }

    @Override
    public String getDailyFortune() {
        return someCrazyStuff.getFortune();
    }

    // LifeCycle methods

    // @PostConstruct -> this method will be invoked at bean creation
    @PostConstruct
    private void myInitMethod() {
        System.out.println("inside BaseBallCoach 'init' method");
    }

    // @PreDestroy -> this method will be invoked at bean ending
    @PreDestroy
    private void myEndMethod() {
        System.out.println("inside BaseBallCoach 'end' method");
    }
}
