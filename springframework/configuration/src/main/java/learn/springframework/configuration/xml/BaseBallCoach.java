package learn.springframework.configuration.xml;

public class BaseBallCoach implements Coach {

    private FortuneService fortuneService;

    public BaseBallCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Spend 30 minutes on batting practise. ";
    }

    @Override
    public String getDailyFortune() {
        return this.fortuneService.getFortune();
    }
}
