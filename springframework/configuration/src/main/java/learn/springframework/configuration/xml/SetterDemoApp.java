package learn.springframework.configuration.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterDemoApp {
    public static void main(String[] args) {

        // setup config
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/configurations/xml/applicationContext.xml");

        // retrieve bean file
        CricketCoach cricketCoach = context.getBean("myCricketCoach", CricketCoach.class);

        System.out.println(cricketCoach.getDailyFortune());
        System.out.println(cricketCoach.getDailyWorkout());
        System.out.println(cricketCoach.getEmailAddress());
        System.out.println(cricketCoach.getTeam());

        context.close();

    }
}
