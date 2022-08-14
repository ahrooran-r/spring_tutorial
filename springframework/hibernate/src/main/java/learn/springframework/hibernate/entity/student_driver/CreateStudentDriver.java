package learn.springframework.hibernate.entity.student_driver;

import learn.springframework.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateStudentDriver {
    public static void main(String[] args) {

        // create Session Factory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        // create Session
        Session session = sessionFactory.getCurrentSession();


        try {

            // create an object
            Student student1 = new Student("ahrooran", "ravindran", "ahro@gmail.com");
            Student student2 = new Student("doe", "xxx", "pqr@gmail.com");
            Student student3 = new Student("darby", "yyy", "qpr@gmail.com");
            Student student4 = new Student("fluffy", "blue", "ddd@gmail.com");
            Student student5 = new Student("potter", "bruh", "pot@gmail.com");

            // saving an object to database
            // begin a transaction
            session.beginTransaction();
            // use session object to save java object
            session.save(student1);
            session.save(student2);
            session.save(student3);
            session.save(student4);
            session.save(student5);

            // commit transaction
            session.getTransaction().commit();

            // retrieve an object from database

            System.out.println("done");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
