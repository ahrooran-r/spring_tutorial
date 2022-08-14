package learn.jpa_hibernate.queries;

import learn.jpa_hibernate.JpaHibernateApplication;
import learn.jpa_hibernate.entity.basics.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
public class JPQLTest {

    private final Logger log = LoggerFactory.getLogger(JPQLTest.class);

    @PersistenceContext
    EntityManager em;

    /**
     * Always better than {@link #findById_basic()}.
     * Try to use this as much as possible
     */
    @Test
    public void findById_typed() {

        // 1. write query and result class
        TypedQuery<Course> typedQuery = em.createQuery("select c from Course c", Course.class);
        // 2. execute query
        List<Course> courses = typedQuery.getResultList();

        log.info("courses -> {}", courses);
    }

    @Test
    public void findById_where() {

        // suppose id is given
        long ID = 1;

        // 1. write query and result class
        TypedQuery<Course> typedQuery = em.createQuery("select c from Course c where c.id=:id", Course.class);
        // 2. inject parameters
        typedQuery.setParameter("id", ID);
        // 3. execute query
        List<Course> courses = typedQuery.getResultList();

        log.info("courses with id=1-> {}", courses);
    }

    @Test
    public void findById_basic() {

        // 1. write query
        Query query = em.createQuery("select c from Course c");
        // 2. execute query
        List courses = query.getResultList();

        log.info("courses -> {}", courses);
    }
}
