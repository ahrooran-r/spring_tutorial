package learn.er_modelling.entity.exercise_3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class SectionId implements Serializable {

    private String courseId;

    private String secId;

    private String semester;

    private Integer year;
}
