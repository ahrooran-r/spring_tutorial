package learn.er_modelling.entity.exercise_2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "premium_payment")
public class PremiumPayment {

    @EmbeddedId
    private PremiumPaymentId id;

    private LocalDateTime dueDate;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Policy policy;

    public PremiumPayment(LocalDateTime dueDate, int amount) {
        this.dueDate = dueDate;
        this.amount = amount;
    }
}