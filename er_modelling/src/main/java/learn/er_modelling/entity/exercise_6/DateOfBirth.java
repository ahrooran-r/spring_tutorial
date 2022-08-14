package learn.er_modelling.entity.exercise_6;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PersistenceException;
import javax.persistence.PrePersist;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class DateOfBirth {

    @Column(precision = 2)
    private Integer birthDay;

    @Column(precision = 2)
    private Integer birthMonth;

    @Column(precision = 4)
    private Integer birthYear;

    @PrePersist
    private void isValid() {
        boolean checkBirthDay = birthDay >= 1 && birthDay <= 31;
        boolean checkBirthMonth = birthMonth >= 1 && birthMonth <= 12;
        boolean checkBirthYear = LocalDate.now().getYear() - birthYear > 13 && birthYear >= LocalDate.now().getYear() - 100;

        if (!checkBirthDay || !checkBirthMonth || !checkBirthYear) {
            throw new PersistenceException("Validation failed");
        }
    }
}
