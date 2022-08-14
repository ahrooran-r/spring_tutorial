package learn.er_modelling.entity.exercise_3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(TakeId.class)
@Entity
@Table(name = "takes")
public class Take {

    @Id
    @Column(length = 5)
    private String id;

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

    @Column(length = 2)
    private String grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {
            @JoinColumn(name = "course_id", referencedColumnName = "course_id"),
            @JoinColumn(name = "sec_id", referencedColumnName = "sec_id"),
            @JoinColumn(name = "semester", referencedColumnName = "semester"),
            @JoinColumn(name = "year", referencedColumnName = "year")

    })
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Student student;
}
