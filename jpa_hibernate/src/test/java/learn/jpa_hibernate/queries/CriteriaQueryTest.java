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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
public class CriteriaQueryTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void select_like_where() {

        // `select s from Subject s where name like '%Com''`


        // 1. Use criteria builder to create a criteria query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Subject> criteriaQuery = cb.createQuery(Subject.class);

        // 2. Define roots for tables which are involved in the query
        Root<Subject> subjectRoot = criteriaQuery.from(Subject.class);

        // 3. Define predicates using criteria builder
        Predicate like = cb.like(subjectRoot.get("name"), "%Com");
        Predicate studentsIsEmpty = cb.isEmpty(subjectRoot.get("students"));

        // 4. Add predicates to criteria query
        criteriaQuery.where(like);
        criteriaQuery.where(studentsIsEmpty);

        // 5. Build TypedQuery using entity manager and criteria query
        TypedQuery<Subject> query = em.createQuery(criteriaQuery.select(subjectRoot));

        List<Subject> subjects = query.getResultList();
        log.info("subjects : {}", subjects);
    }

    @Test
    public void join() {

        // `select sub, stu from Subject s join s.students stu`


        // 1. Use criteria builder to create a criteria query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Subject> criteriaQuery = cb.createQuery(Subject.class);

        // 2. Define roots for tables which are involved in the query
        Root<Subject> subjectRoot = criteriaQuery.from(Subject.class);

        // 3. Define predicates using criteria builder
        Join<Subject, Student> join = subjectRoot.join("students", JoinType.INNER);
        // 3 join types: INNER, LEFT, RIGHT

        // 4. Add predicates to criteria query

        // 5. Build TypedQuery using entity manager and criteria query
        TypedQuery<Subject> query = em.createQuery(criteriaQuery.select(subjectRoot));

        List<Subject> subjects = query.getResultList();
        log.info("subjects : {}", subjects);
    }

}
