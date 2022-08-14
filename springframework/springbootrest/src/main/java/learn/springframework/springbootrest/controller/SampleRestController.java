package learn.springframework.springbootrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class SampleRestController {

    @GetMapping("/hello")
    public String display() {
        return "Hello World";
    }
}
