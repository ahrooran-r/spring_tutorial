package learn.springframework.hibernate.entity.student_driver;

import learn.springframework.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ReadStudentDriver {

    public static void main(String[] args) {

        // create Session Factory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        try (sessionFactory) {

            // create Session
            Session session = sessionFactory.getCurrentSession();

            // reading an object from database
            // begin a transaction
            session.beginTransaction();
            // use session object to get java object -> pass object class and primary_key (in this case id)
            Student student = session.get(Student.class, 1);
            // commit transaction
            session.getTransaction().commit();

            System.out.println(student);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
