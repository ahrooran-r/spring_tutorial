package learn.springboot_rest.controller.versioning;

import learn.springboot_rest.versioning.Name;
import learn.springboot_rest.versioning.PersonV1;
import learn.springboot_rest.versioning.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Versioning via a request parameter
 */
@RestController
public class PersonVersioningController_2 {

    /**
     * URL for this would be: localhost:8080/person/param?version=1
     */
    @GetMapping(value = "/person/param", params = "version=1")
    public PersonV1 personV1() {
        return new PersonV1("KingKong");
    }

    @GetMapping(value = "/person/param", params = "version=2")
    public PersonV2 personV2() {
        return new PersonV2(new Name("King", "Kong"));
    }
}
