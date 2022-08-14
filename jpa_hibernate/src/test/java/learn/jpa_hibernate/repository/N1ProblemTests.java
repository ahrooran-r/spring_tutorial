package learn.jpa_hibernate.repository;


import learn.jpa_hibernate.JpaHibernateApplication;
import learn.jpa_hibernate.entity.relationship.Subject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
class N1ProblemTests {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @DirtiesContext
    public void n1Problem() {
        TypedQuery<Subject> query = em.createNamedQuery("Subject.getAllSubjects", Subject.class);
        List<Subject> subjects = query.getResultList();

        subjects.forEach(subject -> {
            log.info("subject = {}, students take = {}", subject, subject.getStudents());
        });

        // if there are n Subject entities,
        // 1 query to retrieve n entities
        // 1 query for each Subject entity to retrieve Student entity
        // altogether n + 1 queries

        // 2 possible solutions
        // 1. Use eager fetch -> will be problematic because it will always retrieve all students
        // 2. Use graph
        // 3. Join fetch -> manual SQL statement to join fetch
    }

    @Test
    @Transactional
    @DirtiesContext
    public void solving_n1Problem_graph() {

        EntityGraph<Subject> entityGraph = em.createEntityGraph(Subject.class);
        Subgraph<Object> subGraph = entityGraph.addSubgraph("students");


        TypedQuery<Subject> query = em.createNamedQuery("Subject.getAllSubjects", Subject.class);

        List<Subject> subjects = query
                .setHint("javax.persistence.loadgraph", entityGraph) // this makes retrieval of both entities together
                .getResultList();

        subjects.forEach(subject -> {
            log.info("subject = {}, students take = {}", subject, subject.getStudents());
        });
    }

    @Test
    @Transactional
    @DirtiesContext
    public void solving_n1Problem_joinfetch() {

        TypedQuery<Subject> query = em.createNamedQuery("Subject.getAllSubjects.joinFetch", Subject.class);
        List<Subject> subjects = query.getResultList();

        subjects.forEach(subject -> {
            log.info("subject = {}, students take = {}", subject, subject.getStudents());
        });
    }
}
