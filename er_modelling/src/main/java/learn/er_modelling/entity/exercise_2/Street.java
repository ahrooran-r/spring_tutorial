package learn.er_modelling.entity.exercise_2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Street {

    private String apartmentNumber;

    private String streetName;

    @Override
    public String toString() {
        return "#" + apartmentNumber + ", " + streetName + ", ";
    }
}
