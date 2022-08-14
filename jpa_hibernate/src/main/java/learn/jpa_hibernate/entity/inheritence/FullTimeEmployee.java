package learn.jpa_hibernate.entity.inheritence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FullTimeEmployee extends Employee {

    private int salary;

    public FullTimeEmployee(String name, int salary) {
        super(name);
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "FullTimeEmployee{" +
                "salary=" + salary +
                ", name=" + getName() + "}";
    }
}
