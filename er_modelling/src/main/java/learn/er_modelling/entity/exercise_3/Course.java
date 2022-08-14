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
@Table(name = "course")
public class Course {

    @Id
    @Column(length = 8)
    private String courseId;

    @Column(length = 50)
    private String title;

    private Integer credits;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private List<Section> sections = new ArrayList<>();

    // This is a many to many relationship on same table
    // https://medium.com/@kthsingh.ms/modeling-a-child-parent-relationship-in-the-same-table-using-jpa-spring-boot-and-representing-it-15e5a6256dab

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses")
    private List<Course> prerequisites = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "prereq",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "prereq_id")
    )
    private List<Course> courses = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void checkBudget() {
        assert credits > 0;
    }
}