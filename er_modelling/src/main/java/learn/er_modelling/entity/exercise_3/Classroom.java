package learn.er_modelling.entity.exercise_3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ClassroomId.class)
@Entity
@Table(name = "classroom")
public class Classroom {

    // This is another approach from @EmbeddedId
    // see Played.class for further notes

    @Id
    @Column(length = 15)
    private String building;

    @Id
    @Column(length = 7)
    private String roomNumber;

    private Integer capacity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom")
    private List<Section> sections = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        sections.forEach(section -> section.setClassroom(null));
    }
}
