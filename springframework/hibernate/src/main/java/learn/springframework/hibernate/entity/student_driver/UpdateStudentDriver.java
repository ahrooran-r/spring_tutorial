package learn.springframework.hibernate.entity.student_driver;

import learn.springframework.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UpdateStudentDriver {

    public static void main(String[] args) {

        // assume we already know student id
        int id = 3;

        // create session factory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        try (sessionFactory) {

            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            // updating single student
            // retrieve student
            Student student = session.get(Student.class, id);
            // update the parts necessary
            student.setEmail("newQPR@hotmail.com");
            System.out.println(student);

            // update last name for all students
            session
                    .createQuery("update Student set lastName='barbossa'")
                    .executeUpdate();

            session.getTransaction().commit();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
