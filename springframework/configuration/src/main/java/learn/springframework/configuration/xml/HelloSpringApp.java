package learn.springframework.configuration.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloSpringApp {
    public static void main(String[] args) {

        // load spring config file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/configurations/xml/applicationContext.xml");

        // retrieve a bean from spring container
        Coach theCoach = context.getBean("myCoach", Coach.class);


        // call methods on the bean
        System.out.println(theCoach.getDailyWorkout());
        System.out.println(theCoach.getDailyFortune());

        // close the context
        context.close();
    }
}
