package learn.jpa_hibernate.repository.relationship;

import learn.jpa_hibernate.entity.relationship.Passport;
import learn.jpa_hibernate.entity.relationship.Review;
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
@Transactional
public class SubjectRepository {

    @PersistenceContext
    EntityManager em;

    public Subject findById(int id) {
        Subject subject = em.find(Subject.class, id);
        return subject;
    }

    /**
     * This is a different approach than usual setting !!!
     * <p> see {@link StudentRepository#saveWithPassport(Student, Passport)} for similar OneToOne approach
     */
    public void addReviewsToSubject(int subjectId, List<Review> reviews) {

        // `Review` owns `Subject`
        // So to persist a new review -> there should already be a subject in database

        Subject subject = findById(subjectId);
        // Luckily we have a subject, and we first retrieve it
        // If there is no subject -> First persist the subject, then do the following

        reviews.forEach(review -> {

            subject.addReview(review);
            // first associate review with subject
            // because subject -> review is OneToMany, and review is the OWNING part,
            // we have to first add review and then set the relationship on `review` as well -> because its the owner

            // setting the relationship on Review -> because it's the owner
            // in database terms: `reviews` table has foreign key `subject_id`
            // so foreign key must be set
            review.setSubject(subject);

            // now as for the last part -> we need to persist reviews
            // The class is already annotated with @Transactional and @PersistenceContext is used
            // and subject is already persisted beforehand and is now retrieved from the database
            // Therefore the `subject` is already tracked
            // But the newly added `reviews` are not yet tracked
            // So we need to manually persist them for the first time

            em.persist(review);

            // NOTE: We can even persist review first and later add it to subject and set the relationship
            // the order doesn't matter
            // because once persisted, review will be tracked as well -> hence further changes will be automatically persisted
            // in-fact we can do these 3 steps in ANY ORDER and result will be the same
        });
    }

    /**
     * This is a bizarre case involving 2 transactions.
     * <p>
     * When 2 transactions are involved if they are in same instance like this, they will be treated as one
     * <p>
     * So the `student` entity which was retrieved and tracked in the outer method will still be tracked
     * in this inner method as well.
     * <p>
     * Because we are only retrieving both `subject` and `students` entities from datasource
     * and NOT CREATING ANY NEW ENTITIES, both are already been tracked by PersistenceContext.
     * <p>
     * So we DON'T even need to do any merge() or persist(). It will automatically do the transaction.
     */
    public void addStudentsToSubject(int subjectId, List<Student> students) {

        // For details on 2 transactions: https://stackoverflow.com/a/6222624/10582056

        // 1. retrieve subject
        Subject subject = findById(subjectId);

        // 2. create relationships
        students.forEach(student -> {

            subject.addStudent(student);

            student.addSubject(subject);
        });
    }
}