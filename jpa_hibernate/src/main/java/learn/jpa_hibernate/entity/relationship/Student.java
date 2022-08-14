package learn.jpa_hibernate.entity.relationship;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
// ---------------------------
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    // create table students(
    //     id          int          not null auto_increment,
    //     name        varchar(100) not null,
    //     passport_id int          not null unique,
    //     primary key (id),
    //     foreign key (passport_id) references passports (id)
    // );

    // A TABLE (a) WHICH HAS FOREIGN KEY OF ANOTHER TABLE (b), OWNS THE OTHER TABLE (b) [`a` owns `b`]

    // because `students` table has `passport_id` as foreign key =>
    // `Student` now OWNS `Passport`
    // So, here it is annotated with @OneToOne -> but this is not for the case of `Passport`

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, unique = true)
    @ToString.Exclude
    private Passport passport;

    // adding @ManyToMany on both entities alone is not enough -> this will create 2 join tables
    // For example if @ManyToMany is on Student there will be a join table students_subjects
    // Similarly, if @ManyToMany is on Subject there will be a join table subjects_students
    // So if we annotate both classes -> 2 different join tables will be created: `students_subjects` and `subjects_students`
    // Now to mitigate this: we make one the `owning side` of relationship
    // it can be any entity -> because in many-to-many, both tables have foreign keys of each other
    // so, we can randomly select an entity to be the owning side

    // I'm going to make Student entity as owning side
    // So Subjects will have a `mappedBy` attribute pointing to `subjects` attribute in `Student`
    @ManyToMany(fetch = FetchType.LAZY)
    // because @ManyToMany creates a new table -> I can name the created table as follows
    // -> if I don't name, then JPA will create one on its own
    @JoinTable(
            name = "subject_student",
            joinColumns = @JoinColumn(name = "student_id"), // join column on owning side
            inverseJoinColumns = @JoinColumn(name = "subject_id") // join column on other side
    )
    @ToString.Exclude
    private List<Subject> subjects = new ArrayList<>();

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, Passport passport) {
        this.name = name;
        this.passport = passport;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }
}