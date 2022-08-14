package learn.springboot_rest.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// A bean should have a getter -> else automatic conversion to JSON will not work
// You will get an error

@NoArgsConstructor
public class HelloWorldBean {

    @Getter
    @Setter
    private String message;

    public HelloWorldBean(String message) {
        this.message = message;
    }
}
