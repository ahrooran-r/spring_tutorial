package learn.jpa_hibernate.queries;

import learn.jpa_hibernate.JpaHibernateApplication;
import learn.jpa_hibernate.entity.basics.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Native queries are used when: <p>
 * 1. no other choice -> JPA does not support database specific features <p>
 * 2. when we want to do a mass update -> like inserting thousands of rows at once etc. see {@link #whereWeUseNativeQueries()}
 * <p>
 * This comes at an expense of depending on same database. We cannot simply switch to another database.
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
public class NativeQueriesTest {

    @Autowired
    EntityManager em;

    @Test
    public void native_FindAll() {

        Query nativeQuery = em.createNativeQuery("select * from course", Course.class);
        List resultSet = nativeQuery.getResultList();

        log.info("Courses -> {}", resultSet);
    }

    @Test
    public void native_FindById_withNamedParameters() {

        Query nativeQuery = em.createNativeQuery("select * from course where id=:id", Course.class);
        nativeQuery.setParameter("id", 100);

        List resultSet = nativeQuery.getResultList();

        log.info("Courses -> {}", resultSet);
    }

    @Test
    public void native_FindById_withPositionalParameters() {

        Query nativeQuery = em.createNativeQuery("select * from course where id=?", Course.class);
        nativeQuery.setParameter(1, 100);
        // NOTE that parameters are set by position -> not by name -> because ? is placed instead of :id
        // Another thing is that position starts with 1 -> NOT 0. Don't think like normal arrays

        List resultSet = nativeQuery.getResultList();

        log.info("Courses -> {}", resultSet);
    }

    /**
     * suppose I want to update timestamp of `last_updated` on all the rows at once
     */
    @Test
    public void whereWeUseNativeQueries() {

        Query nativeQuery = em.createNativeQuery("update course set last_updated = now()");
        int noOfUpdatedRows = nativeQuery.executeUpdate();

        log.info("# courses updated at once -> {}", noOfUpdatedRows);
    }

}
