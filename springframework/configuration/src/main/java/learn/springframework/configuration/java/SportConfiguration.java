package learn.springframework.configuration.java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("spring.configurations.java")
// specifying properties file
@PropertySource("classpath:spring/configurations/java/sport.properties")
// can directly give like this -> @PropertySource("sport.properties")
public class SportConfiguration {

    // happyFortuneService -> actual bean id  that will be registered in the Spring container
    @Bean
    public FortuneService happyFortuneService() {
        return new HappyFortuneService();
    }

    // swimCoach -> actual bean id  that will be registered in the Spring container
    // bean id 'happyFortuneService' is explicitly passed into 'swimCoach' bean id
    @Bean
    public Coach swimCoach() {
        return new SwimCoach(happyFortuneService());
    }

}
