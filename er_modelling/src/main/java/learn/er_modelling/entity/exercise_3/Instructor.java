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
@Table(name = "instructor")
public class Instructor {

    @Id
    @Column(length = 5)
    private String id;

    @Column(length = 20, nullable = false)
    private String name;

    private String deptName;

    @Column(precision = 8, scale = 2)
    private Integer salary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Course> courses = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "instructors", cascade = CascadeType.REMOVE)
    private List<Section> sections = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "advisor",
            joinColumns = @JoinColumn(name = "i_ID"),
            inverseJoinColumns = @JoinColumn(name = "s_ID", unique = true)
    )
    private List<Student> students = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void checkSalary() {
        assert salary > 29_000;
    }

    @PreRemove
    private void preRemove() {
        students.forEach(student -> student.setInstructors(null));
    }
}