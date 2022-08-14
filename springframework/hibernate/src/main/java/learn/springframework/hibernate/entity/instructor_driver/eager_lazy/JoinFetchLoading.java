package learn.springframework.hibernate.entity.instructor_driver.eager_lazy;

import learn.springframework.hibernate.entity.Course;
import learn.springframework.hibernate.entity.Instructor;
import learn.springframework.hibernate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class JoinFetchLoading {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        try (factory) {

            Session session = factory.getCurrentSession();
            session.beginTransaction();

            // get instructor from db
            int id = 4;

            // write the query JOIN FETCH
            Query<Instructor> query = session.createQuery(
                    "from Instructor i join fetch i.courses where i.id =:theInstructorId",
                    Instructor.class);
            // set the parameters mentioned in the query
            query.setParameter("theInstructorId", id);

            // execute the query
            Instructor instructor = query.getSingleResult();

            session.getTransaction().commit();
            session.close();

            System.out.println(instructor);
            System.out.println(instructor.getCourses());
            System.out.println(instructor.getInstructorDetail());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
