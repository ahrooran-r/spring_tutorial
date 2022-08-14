package learn.er_modelling.exercise_6;

import learn.er_modelling.ErModellingApplication;
import learn.er_modelling.entity.exercise_6.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ErModellingApplication.class)
public class OfficerTests {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    public void insertIntoInstructor() {

        Name name = new Name("Maggie", 'S', "Noodles");

        Street street = new Street("10/31", "Silk lane", 23);
        Address address = new Address(street, "Delhi", "North", 72_000);

        DateOfBirth dob = new DateOfBirth(12, 3, 1980);

        Officer officer = new Officer(name, address, dob);

        em.persist(officer);

    }
}
