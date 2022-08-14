package learn.jpa_hibernate.repository.relationship;

import learn.jpa_hibernate.JpaHibernateApplication;
import learn.jpa_hibernate.entity.relationship.Review;
import learn.jpa_hibernate.entity.relationship.Subject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
public class OneToManyTests {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void getReview() {

        int id = 40_001;
        Review review = reviewRepository.findById(id);

        log.info("Review = {}", review);
    }

    @Test
    @Transactional
    public void getReview_andSubject() {

        int id = 40_001;
        Review review = reviewRepository.findById(id);
        log.info("Review = {}", review);

        // we need @Transactional for this -> because of LAZY fetch
        Subject subject = review.getSubject();
        log.info("Review and Subject = {}, {}", review, subject);
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
