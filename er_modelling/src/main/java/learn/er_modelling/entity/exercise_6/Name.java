package learn.er_modelling.entity.exercise_6;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Name {

    @Column(length = 30, nullable = false)
    private String firstName;

    @Column(length = 1)
    private Character middleInitial;

    @Column(length = 30)
    private String lastName;

    @Override
    public String toString() {
        return String.format("%s %s. %s", firstName, middleInitial, lastName);
    }
}
