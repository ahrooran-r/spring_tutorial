package learn.jpa_hibernate.repository.speing_data;

import learn.jpa_hibernate.entity.basics.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// we pass in the `entity` for which the repository to act upon and `type of ID`
public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {

    // `JpaRepository` extends `CrudRepository`
    // Both are similar, and I can use both interchangeably


    // I can use custom methods too -> mind the naming of methods and parameters given
    // -> The actual implementation will be automatically generated
    List<Course> findByNameAndId(String name, Long id);

    List<Course> findByName(String name);

    List<Course> countByName(String name);

    List<Course> findByNameOrderByIdDesc(String name);

    List<Course> deleteByName(String name);

    // I can have custom methods with custom queries set up

    // JPQL query
    @Query("select c from Course c where c.name like '%Mon'")
    List<Course> courseStartsWith_Com();

    // Native query
    @Query(value = "select * from course where name like '%Mon'", nativeQuery = true)
    List<Course> courseStartsWith_Com_native();
}
