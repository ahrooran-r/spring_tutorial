package learn.jpa_hibernate.queries;

import learn.jpa_hibernate.JpaHibernateApplication;
import learn.jpa_hibernate.entity.relationship.Student;
import learn.jpa_hibernate.entity.relationship.Subject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
public class JPQLRelationshipTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void subjectsWithoutStudents() {

        // NEW KEYWORD: is empty
        TypedQuery<String> query = em.createQuery(
                "select s.name from Subject s where s.students is empty",
                String.class
        );
        List<String> subjects = query.getResultList();
        log.info("subjects without students: {}", subjects);
    }

    @Test
    public void subjectsWithAtLeast2StudentsSorted() {

        int minStudents = 2;

        TypedQuery<String> query = em.createQuery(
                "select s.name from Subject s where s.students.size >= :minStudents order by s.students.size desc",
                String.class
        );
        query.setParameter("minStudents", minStudents);

        List<String> subjects = query.getResultList();
        log.info("subjects with at least 2 students in order: {}", subjects);

        // instead of
        //               `s.students.size >= :minStudents`
        // we can also use
        //              `size(s.students)`
    }

    @Test
    public void subjectsStartWith_Com_() {

        String prefix = "Com%";

        TypedQuery<String> query = em.createQuery(
                "select s.name from Subject s where s.name like :prefix",
                String.class
        );
        query.setParameter("prefix", prefix);

        List<String> subjects = query.getResultList();
        log.info("subjects start with `Com`: {}", subjects);
    }

    @Test
    public void studentsWhosePassportContains_3_() {

        String prefix = "%3%";

        TypedQuery<String> query = em.createQuery(
                "select s.name from Student s where s.passport.number like :prefix",
                String.class
        );
        query.setParameter("prefix", prefix);

        List<String> subjects = query.getResultList();
        log.info("Students whose passport numbers which have `3`: {}", subjects);
    }

    /*
     * other queries
     * 1. like
     * 2. between 1 and 100
     * 3. is null
     * 4. upper, lower
     * 5. trim
     * 6. length
     * */


    // JOINS with JPQL

    /**
     * inner join
     */
    @Test
    public void inner_join() {

        Query query = em.createQuery("select sub, stu from Subject sub inner join sub.students stu");

        // JOIN query returns List of Arrays of Objects: <[sub1, stu1], [sub2, stu2], [sub3, stu3]> etc
        List<Object[]> results = query.getResultList();

        log.info("List size -> {}", results.size());

        for (Object[] result : results) {
            Subject subject = (Subject) result[0];
            Student student = (Student) result[1];
            log.info("Subject: {}, Student: {}", subject, student);
        }
    }

    /**
     * `left join`
     * <p>
     * NOTE: There is NO `right join`
     */
    @Test
    public void left_join() {

        Query query = em.createQuery("select sub, stu from Subject sub left join sub.students stu");

        // JOIN query returns List of Arrays of Objects: <[sub1, stu1], [sub2, stu2], [sub3, stu3]> etc
        List<Object[]> results = query.getResultList();

        log.info("List size -> {}", results.size());

        for (Object[] result : results) {
            Subject subject = (Subject) result[0];
            Student student = (Student) result[1];
            log.info("Subject: {}, Student: {}", subject, student);
        }
    }

    /**
     * `cross join`
     */
    @Test
    public void cross_join() {

        Query query = em.createQuery("select sub, stu from Subject sub, Student stu");

        // JOIN query returns List of Arrays of Objects: <[sub1, stu1], [sub2, stu2], [sub3, stu3]> etc
        List<Object[]> results = query.getResultList();

        log.info("List size -> {}", results.size());

        for (Object[] result : results) {
            Subject subject = (Subject) result[0];
            Student student = (Student) result[1];
            log.info("Subject: {}, Student: {}", subject, student);
        }
    }


}
