package learn.springboot_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

// disable security
@SpringBootApplication(
        exclude = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
        }
)
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }


    // Simplified Internationalization <- automatically captures locale from header field and processes it
    // then hands over the locale info to `LocaleContextHolder`
    // so no need to give it manually
    @Bean
    public AcceptHeaderLocaleResolver acceptHeaderLocaleResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    // // Manual Internationalization <- only works if I give locale manually
    // @Bean
    // public LocaleResolver localeResolver() {
    //     SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    //     localeResolver.setDefaultLocale(Locale.US);
    //     return localeResolver;
    // }

    // Add custom `messages` as sources
    // name of method should be `messageSource`
    // or instead of this method we can simply add a property to application.properties
    // `spring.messages.basename=messages`
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        // `messages` is the base name
        // "messages_fr", "messages_ta" -> extensions of base name
        messageSource.addBasenames("messages");
        return messageSource;
    }
}
