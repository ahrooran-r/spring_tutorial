package learn.jpa_hibernate.repository.relationship;

import learn.jpa_hibernate.entity.relationship.Passport;
import learn.jpa_hibernate.entity.relationship.Student;
import learn.jpa_hibernate.entity.relationship.Subject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
@Transactional // <- This entire class is annotated with `@Transactional`
public class StudentRepository {

    @PersistenceContext
    EntityManager em;

    public Student findById(int id) {
        Student student = em.find(Student.class, id);
        return student;
    }

    public Student save(Student student) {
        if (student.getId() == null) em.persist(student);
        else em.merge(student);
        return student;
    }

    /**
     * similar to {@link SubjectRepository#addReviewsToSubject(int, List)}. <p>
     * NOTE: The approach is different.
     */
    public Student saveWithPassport(Student student, Passport passport) {

        // As you see in Student.class, it owns the passport.
        // So in order to save a student in database, there should already be a passport saved in `passports` table

        // 1. So a newly created passport must be first persisted onto database
        em.persist(passport);

        // 2. Then associate passport with student -> create relationship
        student.setPassport(passport);

        // 3. Now persist the student
        em.persist(student);

        return student;
    }

    /**
     * see SubjectRepository#addStudentsToSubject(int, List)
     */
    public void addSubjectsToStudents(int studentId, List<Subject> subjects) {

        // 1. retrieve subject
        Student student = findById(studentId);

        // 2. add students
        subjects.forEach(subject -> {

            // 2.1 add student to subject
            subject.addStudent(student);

            // 2.2 add subject to student
            // must do both 2.1 and 2.2 because ManyToMany relationship
            student.addSubject(subject);

            // 2.1 subjects are new -> so first persist them
            em.persist(subject);
        });

        // NOTE: now persist the owning side
        // it is important to always persist BOTH the owning side and other mapped side of relationship in ManyToMany
        // even if it is tracked by EntityManager previously
        // This is because there is a 3rd table in this relationship and for that 3rd table to update,
        // we need to manually persist owning side of the relationship
        // This only for the case of ManyToMany relationships when 3rd table needs to be updated
        em.persist(student);
    }
}
