package learn.springframework.springbootrest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
// @EnableWebMvc -> imports all jackson modules to project
@EnableWebMvc
@ComponentScan("spring.rest")
public class AppConfig {
}
