package learn.er_modelling.entity.exercise_6;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Street {

    @Column(length = 5)
    private String streetNumber;

    @Column(length = 50)
    private String streetName;

    @Column(name = "apt_number", precision = 3)
    private Integer apartmentNumber;

    @Override
    public String toString() {
        return String.format("#%d, %s(%s)", apartmentNumber, streetName, streetNumber);
    }
}
