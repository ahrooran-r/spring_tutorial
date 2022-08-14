package learn.springframework.configuration.annotation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationDemoApp {
    public static void main(String[] args) {

        // read spring config file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // get bean from spring container
        Coach tennisCoach = context.getBean("myTennisCoach", Coach.class);
        Coach baseBallCoach = context.getBean("baseBallCoach", Coach.class);
        Coach cricketCoach = context.getBean("cricketCoach", Coach.class);

        /*
         * Coach tennisCoach = context.getBean("tennisCoach", Coach.class);
         * when explicit component name is not given, it will take default name as 'tennisCoach'
         * */

        // call method on bean
        System.out.println(tennisCoach.getDailyWorkout());
        System.out.println(tennisCoach.getDailyFortune());
        //
        System.out.println(baseBallCoach.getDailyWorkout());
        System.out.println(baseBallCoach.getDailyFortune());

        System.out.println(cricketCoach.getDailyWorkout());
        System.out.println(cricketCoach.getDailyFortune());

        // close context
        context.close();

    }
}
