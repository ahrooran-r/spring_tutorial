package learn.er_modelling.entity.exercise_2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {

    private String postalCode;

    @Embedded
    private Street street;

    private String city;

    private String district;

    @Override
    public String toString() {
        return street + "\n" + city + ", \n" + district;
    }
}
