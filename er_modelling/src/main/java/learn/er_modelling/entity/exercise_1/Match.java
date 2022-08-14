package learn.er_modelling.entity.exercise_1;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, length = 30)
    private String stadium;

    @Column(nullable = false, length = 30)
    private String opponentTeam;

    private int ownScore;

    private int opponentScore;

    @ToString.Exclude
    @OneToMany(mappedBy = "match")
    private List<Played> playeds = new ArrayList<>();

}