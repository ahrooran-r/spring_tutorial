package learn.jpa_hibernate.entity.relationship;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Data
// ---------------------------
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, insertable = false, unique = true)
    @Setter(AccessLevel.NONE)
    private Integer id;

    /*
     * DECIMAL(5,2)
     * In this example, 5 is the precision and 2 is the scale.
     * The precision represents the number of significant digits that are stored for values, and
     * the scale represents the number of digits that can be stored following the decimal point.
     * https://dev.mysql.com/doc/refman/8.0/en/fixed-point-types.html
     * */
    @Column(nullable = false, precision = 1)
    private int rating;

    @Column(length = 250)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    // by default fetch type is EAGER
    @ToString.Exclude
    private Subject subject;

    // Many Reviews map to one course
    // If you take a look at SQL for Review -> relationship.sql
    // you can see `reviews` table has `foreign key` attribute => hence `review` is the owning part in this relationship
    // so `mapped by` should be added to Subject


    public Review(int rating, String description) {
        this.rating = rating;
        this.description = description;
    }
}
