package learn.jpa_hibernate.entity.inheritence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PartTimeEmployee extends Employee {

    private int hourlyWage;

    public PartTimeEmployee(String name, int hourlyWage) {
        super(name);
        this.hourlyWage = hourlyWage;
    }

    @Override
    public String toString() {
        return "PartTimeEmployee{" +
                "hourlyWage=" + hourlyWage +
                ", name=" + getName() + "}";
    }
}
