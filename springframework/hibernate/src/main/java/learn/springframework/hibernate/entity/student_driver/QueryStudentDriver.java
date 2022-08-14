package learn.springframework.hibernate.entity.student_driver;

import learn.springframework.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class QueryStudentDriver {
    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        try (sessionFactory) {

            Session session = sessionFactory.getCurrentSession();

            // query database and add values to a list
            session.beginTransaction();
            List<Student> students = session.createQuery("from Student").getResultList();
            session.getTransaction().commit();

            // print all students
            for (Student student : students) System.out.println(student);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
