package learn.jpa_hibernate.repository.relationship;

import learn.jpa_hibernate.JpaHibernateApplication;
import learn.jpa_hibernate.entity.relationship.Review;
import learn.jpa_hibernate.entity.relationship.Student;
import learn.jpa_hibernate.entity.relationship.Subject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
public class ManyToManyTests {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void test_addSubjectsToStudents() {

        int studentId = 20_001;

        Subject s1 = new Subject("Cooking");
        Subject s2 = new Subject("Music");
        List<Subject> subjects = Arrays.asList(s1, s2);

        studentRepository.addSubjectsToStudents(studentId, subjects);
    }

    @Test
    @Transactional
    public void getStudentAndSubjects() {

        // adding subjects to owning side student

        int id = 20_001;
        Student student = studentRepository.findById(id);

        log.info("Student = {}", student);

        List<Subject> subjects = student.getSubjects();
        log.info("Student = {}, taken subjects = {}", student, subjects);

        // NOTE: A practical problem I've encountered:
        // if you do not see your result after adding something, then better check whether you used @Transactional in excessive places
        // If in doubt: do a quick em.flush() and check whether if it persists.
        // If so, then you have added some additional unnecessary @Transactional
    }

    @Test
    @Transactional
    @Rollback(value = false)
    // By default, all tests will roll back if they are annotated with @Transactional.
    // So we have to explicitly set it to false.
    public void test_addStudentsToSubject() {

        int subjectId = 10_002;

        Student s1 = studentRepository.findById(20_002);
        Student s2 = studentRepository.findById(20_003);
        List<Student> students = Arrays.asList(s1, s2);

        subjectRepository.addStudentsToSubject(subjectId, students);
    }


    @Test
    @Transactional
    public void getSubjectsAndStudent() {

        int id = 10_002;
        Subject subject = subjectRepository.findById(id);

        log.info("Subject = {}", subject);

        List<Student> students = subject.getStudents();
        log.info("Subject = {}, taken students = {}", subject, students);
    }


    @Test
    // @DirtiesContext -> I'm not doing this because I want the changes made by this test to reflect on the next test below
    public void test_addReviewsToSubject() {

        int subjectId = 10_002;

        Review r1 = new Review(5, "Muah!!!");
        Review r2 = new Review(3, "Good enough!!!");
        List<Review> reviews = Arrays.asList(r1, r2);

        subjectRepository.addReviewsToSubject(subjectId, reviews);

    }

    @Test
    @Transactional
    public void getSubject_andReviews() {

        int id = 10_002;
        Subject subject = em.find(Subject.class, id);
        log.info("Subject = {}", subject);
        // I'm directly using entity manager rather than separate SubjectRepository

        // we need @Transactional for this -> because of LAZY fetch
        List<Review> reviews = subject.getReviews();
        log.info("Reviews = {}", reviews);
    }
}
