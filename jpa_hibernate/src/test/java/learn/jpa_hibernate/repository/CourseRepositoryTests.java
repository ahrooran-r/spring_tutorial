package learn.jpa_hibernate.repository;


import static org.junit.jupiter.api.Assertions.*;

import learn.jpa_hibernate.JpaHibernateApplication;
import learn.jpa_hibernate.entity.basics.Course;
import learn.jpa_hibernate.entity.basics.CourseCode;
import learn.jpa_hibernate.repository.basics.CourseJpaRepository;
import learn.jpa_hibernate.repository.speing_data.CourseSpringDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
class CourseRepositoryTests {

    @Autowired
    CourseJpaRepository courseRepository;

    @Autowired
    CourseSpringDataRepository courseSpringDataRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void find() {
        Course course = courseRepository.findById(1);
        log.info("course = {}", course);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void updateAndFind() {

        Course course = em.find(Course.class, 1L);
        course.setCourseCode(new CourseCode("CS", "101"));

        em.flush();
        log.info("course = {}", course);
    }


    /**
     * @ DirtiesContext -> resets the system to previous original state
     */
    @Test
    @DirtiesContext
    void deleteById_basic() {
        long id = 1L;
        courseSpringDataRepository.deleteById(id);
        assertNull(courseRepository.findById(id));
    }

    @Test
    @DirtiesContext
    void findById_nativeQuery() {
        long id = 1L;

        Query query1 = em.createNativeQuery("select * from course where id=:id", Course.class);
        query1.setParameter("id", id);
        Course course1 = (Course) query1.getSingleResult();

        assertNotNull(course1);
        // @SQLDelete or @Where will not work for native query
        // So in this case the resulting entity will not be null
        // we have to manually add where clause

        Query query2 = em.createNativeQuery("select * from course where id=:id and is_deleted=false", Course.class);
        query2.setParameter("id", id);
        List courseList = query2.getResultList();

        assertTrue(courseList.isEmpty());
        // Not result will be null because we are manually filtering where clause
    }
}
