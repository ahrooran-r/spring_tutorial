package learn.er_modelling.entity.exercise_3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {

    @Id
    @Column(length = 20)
    private String deptName;

    @Column(length = 15)
    private String building;

    private Integer budget;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Instructor> instructors = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Student> students = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void checkBudget() {
        assert budget > 0;
    }

    // This will free courses from department if a department is deleted
    // https://stackoverflow.com/a/47435269/10582056
    @PreRemove
    private void preRemove() {
        courses.forEach(course -> course.setDepartment(null));
        instructors.forEach(instructor -> instructor.setDepartment(null));
        students.forEach(student -> student.setDeptName(null));
    }
}