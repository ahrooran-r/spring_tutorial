package learn.er_modelling.entity.exercise_6;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Address {

    @Embedded
    private Street street;

    @Column(length = 30)
    private String city;

    @Column(length = 30)
    private String district;

    @Column(precision = 10)
    private Integer zip;

    @Override
    public String toString() {
        return String.format("%s,\n%s,\n%s(%d)", street, city, district, zip);
    }
}
