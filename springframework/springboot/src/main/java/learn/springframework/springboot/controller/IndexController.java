package learn.springframework.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "Hello Spring Boot! " + new Timestamp(new Date().getTime());
    }
}
