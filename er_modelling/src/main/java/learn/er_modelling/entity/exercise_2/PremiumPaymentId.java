package learn.er_modelling.entity.exercise_2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class PremiumPaymentId implements Serializable {

    private int id;

    @Column(nullable = false)
    private int policyId;

}