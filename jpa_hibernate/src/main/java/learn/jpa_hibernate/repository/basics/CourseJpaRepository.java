package learn.jpa_hibernate.repository.basics;

import learn.jpa_hibernate.entity.basics.Course;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
// @Transactional -> a transaction is automatically `started` when entering the method
//                      and `committed / rolled back` at the end.
// NOTE: make sure to import Spring annotation -> import org.springframework.transaction.annotation.Transactional;
@Transactional(isolation = Isolation.READ_COMMITTED)
public class CourseJpaRepository {

    @PersistenceContext
    EntityManager em;

    public Course findById(long id) {
        Course course = em.find(Course.class, id);
        return course;
    }

    /**
     * <a href="https://stackoverflow.com/a/1070629/10582056">https://stackoverflow.com/a/1070629/10582056</a>
     */
    public Course save(Course course) {

        // if a course object does not have an `id` then it is just created and not already in the database
        // that means it is a new row -> it should be persisted
        if (course.getId() == null) em.persist(course);
            // if there is already an `id` field -> then it is an old row
            // then it should be updated
        else em.merge(course);

        return course;
    }

    public void deleteById(long id) {
        Query deleteQuery = em.createNamedQuery("Course.deleteById").setParameter("id", id);
        deleteQuery.executeUpdate();
    }

    /**
     * Now you can see that even after persisting, any changes I make to `course` object are automatically tracked and
     * persisted by the `EntityManager`. <p>
     * This is because of the @Transactional annotation. <p>
     * Hence, while I am within the method boundaries, any change I make to object after `persist()`
     * (only for `persist()` NOTE `merge()` works a bit different) will be tracked.
     * For merge() -> see {@link #save(Course)} docs
     */
    public void trackAfterPersist() {
        Course course = new Course("KingKong");
        em.persist(course);
        course.setName("KingKong - Version 2");
    }

    public void entityManagerMethods() {

        Course course1 = new Course("KingKong");
        em.persist(course1);

        // The data is not sent to database immediately after persistence.
        // `flush()` makes it possible to flush the changes to database.
        em.flush();

        course1.setName("KingKong - Version 2");
        em.flush();

        Course course2 = new Course("Godzilla");
        em.persist(course2);
        em.flush();

        // now I'm going to do a detach
        em.detach(course2);
        // This makes PersistenceContext to stop tracking `course2` object
        // Therefore any further changes will not be automatically tracked sent to te database
        // we have to explicitly call `em.merge()` if we want to send the rest of the changes

        course2.setName("Godzilla - Version 2");
        em.flush();

        // note that course1 is still managed. suppose I update course1 again
        course1.setName("KingKong - Reboot");
        // Note that I haven't called an explicit flush() after last update -> so last edit is still not sent to actual database
        // it is still here, and will be automatically sent to database when the method ends

        // now I have changed my mind and don't want the data to go through.
        // so I now call this method:
        em.refresh(course1);
        // this will bring in a fresh copy of course1 and replace current object with the fresh copy
        // so the last edit I made will not be in this copy
        // however, since I called flush() before and explicitly synced the state of course1 with database,
        // those initial updates will be present in the retrieved course1 object


        // If I want to detach everything (course1, course2, ... ), then I have to call
        em.clear();

        // About flush: https://stackoverflow.com/a/4275973/10582056
        // Note that even after I flush some part of the code, and in latter part there are some errors,
        // Hibernate will roll back to the beginning of the method REGARDLESS of previous flushes executed or not
        // So, in case of errors it doesn't matter we flushed the previous part of method
    }
}
