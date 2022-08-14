package learn.er_modelling.entity.exercise_3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(SectionId.class)
@Entity
@Table(name = "section")
public class Section {

    @Id
    @Column(length = 8)
    private String courseId;

    @Id
    @Column(length = 8)
    private String secId;

    @Id
    @Column(length = 6)
    private String semester;

    @Id
    @Column(precision = 4)
    private Integer year;

    @Column(length = 15)
    private String building;

    @Column(length = 7)
    private String roomNumber;

    @Column(length = 4)
    private String timeSlotId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Course course;

    // https://www.baeldung.com/jpa-join-column
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumns(value = {
            @JoinColumn(name = "building", referencedColumnName = "building"),
            @JoinColumn(name = "room_number", referencedColumnName = "room_number")

    })
    private Classroom classroom;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "teaches",
            joinColumns = {
                    @JoinColumn(name = "course_id"),
                    @JoinColumn(name = "sec_id"),
                    @JoinColumn(name = "semester"),
                    @JoinColumn(name = "year")
            },
            inverseJoinColumns = @JoinColumn(name = "ID")
    )
    private List<Instructor> instructors = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "section", cascade = CascadeType.REMOVE)
    private List<Take> takes = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void prePersist() {

        Set<String> semesters = new HashSet<>(List.of("Fall", "Winter", "Spring", "Summer"));
        assert semesters.contains(semester);

        assert year > 1701 && year < 2100;
    }
}