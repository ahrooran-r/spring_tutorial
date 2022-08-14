package learn.er_modelling.entity.exercise_3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(TimeSlotId.class)
@Entity
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    @Column(length = 4)
    private String timeSlotId;

    @Id
    @Column(length = 1)
    private String day;

    @Id
    @Column(precision = 2)
    private Integer startHr;

    @Id
    @Column(precision = 2)
    private Integer startMin;

    @Column(precision = 2)
    private Integer endHr;

    @Column(precision = 2)
    private Integer endMin;

    @PrePersist
    @PreUpdate
    private void check() {
        assert startHr >= 0 && startHr < 24;
        assert startMin >= 0 && startMin < 60;
        assert endHr >= 0 && endHr < 24;
        assert endMin >= 0 && endMin < 60;
    }

}
