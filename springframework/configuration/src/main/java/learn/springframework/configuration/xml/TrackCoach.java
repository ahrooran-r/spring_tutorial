package learn.springframework.configuration.xml;

public class TrackCoach implements Coach {

    private FortuneService fortuneService;

    public TrackCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Run a hard 5k";
    }

    @Override
    public String getDailyFortune() {
        return this.fortuneService.getFortune();
    }

    public void myStartupMethod() {
        System.out.println("TrackCoach: inside myStartupMethod");
    }

    void myDestroyMethod() {
        System.out.println("TrackCoach: inside myDestroyMethod");
    }
}
