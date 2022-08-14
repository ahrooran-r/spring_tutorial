package learn.springframework.configuration.java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Driver {
    public static void main(String[] args) {

        // read spring config java class
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SportConfiguration.class);

        // get bean from spring container
        Coach swimCoach = context.getBean("swimCoach", Coach.class);

        // call method on bean
        System.out.println(swimCoach.getDailyWorkout());
        System.out.println(swimCoach.getDailyFortune());
        System.out.println(((SwimCoach) swimCoach).getProperties());

        // close context
        context.close();

    }
}
