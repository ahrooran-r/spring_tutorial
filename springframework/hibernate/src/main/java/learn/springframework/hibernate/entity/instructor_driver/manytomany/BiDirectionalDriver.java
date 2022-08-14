package learn.springframework.hibernate.entity.instructor_driver.manytomany;

import learn.springframework.hibernate.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class BiDirectionalDriver {

    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Review.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        try (sessionFactory; session) {
            session.beginTransaction();

            // students and courses are already saved in database
            // so retrieve them and do a many to many binding on them
            List<Student> students = session.createQuery("from Student", Student.class).getResultList();
            List<Course> courses = session.createQuery("from Course", Course.class).getResultList();

            students.get(1).addCourse(courses.get(0));
            students.get(1).addCourse(courses.get(1));

            courses.get(1).addStudent(students.get(0));
            courses.get(1).addStudent(students.get(1));
            courses.get(1).addStudent(students.get(2));

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
