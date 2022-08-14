package learn.springframework.hibernate.entity.instructor_driver.onetomany;

import learn.springframework.hibernate.entity.Course;
import learn.springframework.hibernate.entity.Instructor;
import learn.springframework.hibernate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class BiDirectionalDriver {

    public static void main(String[] args) {

        // create some courses
        Course c1 = new Course("Hello");
        Course c2 = new Course("World");
        Course c3 = new Course("!!!");

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try (factory; session) {

            session.beginTransaction();

            // get the instructor
            Instructor instructor = session.get(Instructor.class, 4);

            // add courses to instructor
            instructor.add(c1);
            instructor.add(c2);
            instructor.add(c3);

            // save the courses
            session.save(c1);
            session.save(c2);
            session.save(c3);

            // get one of the course and delete it
            Course c = session.get(Course.class, 10);
            session.delete(c);

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
