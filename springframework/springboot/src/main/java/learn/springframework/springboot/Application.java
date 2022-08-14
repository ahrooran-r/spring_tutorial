package learn.springframework.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        //scanBasePackages = {"org.acme.iot.utils", "edu.cmu.wean"}
)
/*
 * @SpringBootApplication -> composed of following annotations
 *       1. @EnableAutoConfiguration -> enable spring boot auto configuration support
 *       2. @ComponentScan(...) -> enable component scanning of current package, also scans included sub-packages recursively
 *       3. @Configuration -> register extra beans with @Bean annotation, or import other config files
 * */
/*
 * Always place java packages inside package where Application.class exist
 * Since @ComponentScan automatically searches for packages here, there is no need to explicitly add other packages,
 *      if already placed inside base package
 * if you want to explicitly add other packages -> place them under `scanBasePackages`
 * */
public class Application {

    public static void main(String[] args) {

        /*
         * Bootstrap the application
         *       1. Create application context
         *       2. Register all the beans
         *       3. Starts embedded server -> tomcat server
         * */
        SpringApplication.run(Application.class, args);
    }

}
