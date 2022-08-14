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
@Table(name = "student")
public class Student {

    @Id
    @Column(length = 5)
    private String id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20)
    private String deptName;

    @Column(precision = 3)
    private Integer totCred;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Take> takes;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students", cascade = CascadeType.REMOVE)
    private List<Instructor> instructors = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void prePersist() {
        assert totCred >= 0;
    }
}