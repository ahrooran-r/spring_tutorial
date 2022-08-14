package learn.jpa_hibernate.entity.relationship;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
// -------------------------------------------------------------------------------
// can replace `@Getter`, `@Setter`, `@ToString` with `@Data`
// BUT make sure to annotate `id` field as `@Setter(value = AccessLevel.NONE)`
@Entity
@Table(name = "passports")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // always specify what type of strategy is used
    private Integer id;

    @Column(nullable = false, unique = true)
    private String number;

    // If I have a `Student` field and annotate @OneToOne -> this will create a duplicate column in Students
    // That is not good for database
    // Because `Passport` is owned by `Student` -> we take a different approach

    // `Student` is the OWNING side -> so I added owning attribute 'passport' to here
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
    @ToString.Exclude
    private Student student;

    // This might be confusing so here is a good example to memorize which is which ;)
    // Say a Country X goes to war with country Y and wins
    // So now X owns Y
    // Now to rule Y, country X sends an ambassador in its place as a representation of ownership
    // A similar situation is happening here

    // Because `Student` owns `Passport` => an attributed of `Student` is mentioned in `Passport`
    // mappedBy = "passport"
    // here "passport" is the `private Passport passport;` attribute in Student class.


    public Passport(String number) {
        this.number = number;
    }
}
