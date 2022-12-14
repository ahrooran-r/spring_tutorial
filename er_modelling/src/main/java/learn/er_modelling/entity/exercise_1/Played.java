package learn.er_modelling.entity.exercise_1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * <a href="https://stackoverflow.com/a/5127262/10582056">https://stackoverflow.com/a/5127262/10582056</a>
 * <p>
 * with JPA, in order to have the chance to have extra columns, you need to use two OneToMany associations,
 * instead of a single ManyToMany relationship. You can also add a column with autogenerated values; this way,
 * it can work as the primary key of the table, if useful.
 */
@Getter
@Setter
@Entity
@Table(name = "played")
public class Played {

    // because this table does not have a separate primary key (aka @Id) and only a composite primary key,
    // We use a separate embedded class to mark the primary key
    // So, NOTE: 2 things are new and being used here
    // 1. Using 2 OneToMany tables instead of a @ManyToMany approach because of extra `score` attribute
    // 2. Using an EmbeddedId because this only has composite primary key
    // https://www.baeldung.com/jpa-composite-primary-keys
    // Benefit of embedded id: https://stackoverflow.com/a/49615687/10582056
    // https://stackoverflow.com/a/212371/10582056
    @EmbeddedId
    private PlayedId playedId;

    @Column(nullable = false)
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    private Match match;
}
