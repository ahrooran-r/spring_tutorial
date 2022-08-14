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
@Setter()
@ToString
// ---------------------------
@Entity
@Table(name = "subjects")
@NamedQueries(
        value = {
                @NamedQuery(name = "Subject.getAllSubjects", query = "select s from Subject s"),
                @NamedQuery(name = "Subject.getAllSubjects.joinFetch", query = "select s from Subject s join fetch s.students")
        }
)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    /**
     * see Review#course
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    // by default fetch type is LAZY
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();
    // There 2 annotations: @OneToMany and @ManyToOne
    // because one subject has many reviews -> we use @OneToMany here
    // Review is the owning part -> hence its attribute `subject` is set to `mapped by`

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subjects")
    @ToString.Exclude
    private List<Student> students = new ArrayList<>();

    public Subject(String name) {
        this.name = name;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }
}
