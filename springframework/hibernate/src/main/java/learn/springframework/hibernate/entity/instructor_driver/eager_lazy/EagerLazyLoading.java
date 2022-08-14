package learn.springframework.hibernate.entity.instructor_driver.eager_lazy;


import learn.springframework.hibernate.entity.Course;
import learn.springframework.hibernate.entity.Instructor;
import learn.springframework.hibernate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class EagerLazyLoading {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try (factory; session) {

            session.beginTransaction();

            Instructor instructor = (Instructor) session
                    .createQuery("from Instructor").setMaxResults(1).getResultList().get(0);

            System.out.println(instructor);
            System.out.println(instructor.getInstructorDetail());
            System.out.println(instructor.getCourses());

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

/*
 * Two ways to overcome exception during Lazy loading
 * 1. load data before session is closed.
 *       We can load same data again after closing session since that data is already loaded into memory
 * 2. Use FETCH JOIN method of HQL
 * */
