package learn.springframework.springbootrest.controller;

import learn.springframework.springbootrest.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    List<Student> students;

    @PostConstruct
    public void creation() {

        // create the list at the creation of bean
        students = new ArrayList<>(3);

        students.add(new Student("Ahroo", "Ravi"));
        students.add(new Student("Barbossa", "Hector"));
        students.add(new Student("King", "Fish"));
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        // return the list
        // Jackson will automatically turn this POJO into JSON format
        return students;
    }

    @GetMapping("/student/{theId}")
    // @PathVariable -> binds {id} to 'id' in method parameter
    // or set value = "theId" in @PathVariable
    // both names must match by default
    public Student getStudent(@PathVariable(value = "theId") int id) {
        try {
            return students.get(id);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new StudentNotFoundException("Student not found in given id: " + id, indexOutOfBoundsException);
        }
    }
}
