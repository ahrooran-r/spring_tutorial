package learn.springboot_rest.controller;

import learn.springboot_rest.repository.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping(method = RequestMethod.GET, path = "/hello")
// If we don't specify a `method`, it's going to map to any HTTP request.
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    // Whenever we use a REST service, we define 2 things:
    // 1. GET, POST, etc
    // 2. URL

    // Instead of using @RequestMapping like above
    // we can directly use @GetMapping
    // It is more readable and easy
    @GetMapping("/world")
    public String helloWorld() {
        return "Hello World";
    }

    /**
     * @return A bean instead of raw String
     */
    @GetMapping("/world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World Bean");
    }

    /**
     * @param name is a path variable denoted by {name}
     */
    @GetMapping("/world/{name}")
    public String helloWorldBeanWithPathParam(@PathVariable String name) {
        return "HelloWorld -> Your name is: " + name;
    }

    @GetMapping("/world-inter")
    // @RequestHeader -> indicates that a method parameter should be bound to a web request header
    // We can give value directly as "Accept-Language" or use existing enum
    public String helloWorldInternationalized(@RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale) {
        return messageSource.getMessage("good.morning.message", null, locale);
    }
}
