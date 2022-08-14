package learn.springframework.configuration.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanScopeDemoApp {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/configurations/xml/beanScope-applicationContext.xml");

        /*
         * when scope="singleton" -> true, same addresses
         * when scope="prototype" -> false, different addresses
         * */
        Coach coach_1 = context.getBean("myCoach", Coach.class);
        Coach coach_2 = context.getBean("myCoach", Coach.class);

        System.out.println(coach_1 == coach_2);
        System.out.println(coach_1);
        System.out.println(coach_2);

        context.close();
    }
}
