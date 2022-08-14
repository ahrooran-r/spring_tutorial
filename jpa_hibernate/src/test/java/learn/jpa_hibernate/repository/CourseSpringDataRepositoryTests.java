package learn.jpa_hibernate.repository;

import learn.jpa_hibernate.JpaHibernateApplication;
import learn.jpa_hibernate.entity.basics.Course;
import learn.jpa_hibernate.repository.speing_data.CourseSpringDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaHibernateApplication.class)
class CourseSpringDataRepositoryTests {

    @Autowired
    CourseSpringDataRepository springDataRepository;

    @Test
    public void findById() {
        long id = 1L;
        Optional<Course> course = springDataRepository.findById(id);
        if (course.isPresent()) log.info("Course = {}", course.get());
    }

    @Test
    public void sort() {

        // I can chain many sorts one after another
        Sort sort1 = Sort.by(Sort.Direction.DESC, "name");
        Sort sort2 = Sort.by(Sort.Direction.DESC, "createdOn");

        // then create a combined sort
        Sort combinedSort = sort1.and(sort2);

        // now pass it on
        List<Course> courses = springDataRepository.findAll(combinedSort);
        log.info("sorted Courses = {}", courses);
    }

    /**
     * Divide results into different pages -> i.e., n=10 results at a time
     */
    @Test
    public void pagination() {

        PageRequest pageRequest = PageRequest.of(0, 3);
        // page with id=0 will have 3 results
        Page<Course> firstPage = springDataRepository.findAll(pageRequest);
        log.info("first page = {}", firstPage.getContent());

        /*
            util methods
            ---------------------------------
            firstPage.getTotalPages();
            firstPage.getTotalElements();
            firstPage.getContent();
        */

        // I can call `2nd page` from 1st page as well
        Pageable secondPageable = firstPage.nextPageable();
        Page<Course> secondPage = springDataRepository.findAll(secondPageable);
    }

    /*

        Standard CRUD methods in both `JpaRepository` and `CrudRepository`

        CREATE, UPDATE
        ------------------
        1. saveAll(Iterable<S> entities);
        2. saveAndFlush(S entity);
        3. saveAllAndFlush(Iterable<S> entities);

        READ
        ---------------
        4. findAll();
        5. findAllById(Iterable<ID> ids);
        6. getById(ID id);

        DELETE
        --------------
        7. deleteById(ID id);
        8. delete(T entity);
        9. deleteAll();
    */


}
