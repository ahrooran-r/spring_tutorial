package learn.springframework.hibernate.entity.instructor_driver.onetomany;


import learn.springframework.hibernate.entity.Course;
import learn.springframework.hibernate.entity.Instructor;
import learn.springframework.hibernate.entity.InstructorDetail;
import learn.springframework.hibernate.entity.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class UniDirectionalDriver {
    public static void main(String[] args) {

        // create reviews
        Review r1 = new Review("Good course");
        Review r2 = new Review("yay");
        Review r3 = new Review("hi back");


        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Review.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try (session; sessionFactory) {

            //session.beginTransaction();
            //
            //// retrieve course from database
            //Course course = (Course) session.createQuery("from Course where id=8").getSingleResult();
            //
            //// add reviews to course
            //course.addReview(r1);
            //course.addReview(r2);
            //course.addReview(r3);
            //
            //// save reviews
            //session.save(r1);
            //session.save(r2);
            //session.save(r3);
            //
            //session.getTransaction().commit();

            // fetch join reviews

            session.beginTransaction();

            int courseId = 8;
            Query<Course> query = session.createQuery(
                    "from Course c join fetch c.reviews where c.id=:courseId",
                    Course.class
            );
            query.setParameter("courseId", courseId);
            Course course = query.getSingleResult();

            session.getTransaction().commit();

            List<Review> reviewList = course.getReviews();
            for (Review r : reviewList) System.out.println(r.getComment());

        }

    }
}
