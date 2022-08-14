package learn.springboot_rest.controller.versioning;

import learn.springboot_rest.versioning.Name;
import learn.springboot_rest.versioning.PersonV1;
import learn.springboot_rest.versioning.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The basic versioning method called `URI versioning`. Manually version each controller using get mapping
 * <p>
 * disadvantages: URI pollution
 * <p>
 * advantages: easy to document
 */
@RestController
public class PersonVersioningController_1 {

    @GetMapping("/v1/person")
    public PersonV1 personV1() {
        return new PersonV1("KingKong");
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2() {
        return new PersonV2(new Name("King", "Kong"));
    }
}
