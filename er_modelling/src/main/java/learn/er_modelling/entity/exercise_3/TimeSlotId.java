package learn.er_modelling.entity.exercise_3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class TimeSlotId implements Serializable {

    private String timeSlotId;

    private String day;

    private Integer startHr;

    private Integer startMin;

}
