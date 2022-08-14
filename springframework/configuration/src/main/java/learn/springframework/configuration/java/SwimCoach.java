package learn.springframework.configuration.java;

import org.springframework.beans.factory.annotation.Value;

public class SwimCoach implements Coach {

    private FortuneService fortuneService;

    @Value("${coach.swim.email}")
    private String email;

    @Value("${coach.swim.team}")
    private String team;

    public SwimCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Practise swimming!";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }

    public String getProperties() {
        return String.format("email: %s, team: %s", this.email, this.team);
    }
}
