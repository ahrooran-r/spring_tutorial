package learn.springframework.hibernate.entity.instructor_driver.onetoone;

import learn.springframework.hibernate.entity.Course;
import learn.springframework.hibernate.entity.Instructor;
import learn.springframework.hibernate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UniDirectionalDriver {

    public static void main(String[] args) {

        // create sessionFactory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();

        // create objects
        InstructorDetail instructorDetail = new InstructorDetail("barbossa@youtube.com", "Play");
        Instructor instructor = new Instructor("barbossa", "hector", "bar@hec.com");

        // associate the objects
        instructor.setInstructorDetail(instructorDetail);

        // Uni-Directional Mapping
        try (sessionFactory) {

            // create session
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(instructor);
            session.getTransaction().commit();

            // retrieve instructor object and delete it
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String query = "from Instructor where email='bar@hec.com'";
            Instructor retrievedInstructor = (Instructor) session
                    .createQuery(query)
                    .setMaxResults(1)
                    .getResultList()
                    .get(0);
            session.delete(retrievedInstructor);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
