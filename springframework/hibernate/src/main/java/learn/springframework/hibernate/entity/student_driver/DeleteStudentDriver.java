package learn.springframework.hibernate.entity.student_driver;

import learn.springframework.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DeleteStudentDriver {
    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration().
                configure("hibernate.cfg.xml").
                addAnnotatedClass(Student.class).
                buildSessionFactory();

        try (sessionFactory) {

            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            // delete when id is known
            int id1 = 1, id2 = 5;
            Student student = session.get(Student.class, id1);
            session.delete(student);

            // delete multiple objects
            String query = "delete from Student where id=" + id2;
            session.createQuery(query).executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
