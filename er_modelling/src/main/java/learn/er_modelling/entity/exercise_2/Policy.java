package learn.er_modelling.entity.exercise_2;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
    private List<Car> cars;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
    private List<PremiumPayment> payments;
}
