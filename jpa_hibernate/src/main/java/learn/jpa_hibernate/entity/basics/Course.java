package learn.jpa_hibernate.entity.basics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Getter
@Setter
// -----------------------------------------------

@Cacheable
// enables 2nd level cache

@SQLDelete(sql = "update course set is_deleted=true where id=?", check = ResultCheckStyle.COUNT)
// Hibernate only -> not JPA
// I can execute specific query when deleting or other such events instead of actually doing said event.
// This is called Soft Delete -> https://thorben-janssen.com/implement-soft-delete-hibernate/
// More on soft delete: https://www.jpa-buddy.com/blog/soft-deletion-in-hibernate-things-you-may-miss/
@Where(clause = "is_deleted=false")
// Hibernate only -> not JPA
// when doing a `select *`, it only retrieves non-marked rows
// NOTE: This approach does not work for `NativeQueries` and `NamedQueries`. So you have to manually add where clause
// NOTE: the queries are native SQL, not JPQL. So this depends on underlying Database implementation

@NamedQueries(
        value = {
                @NamedQuery(name = "Course.deleteById", query = "delete from Course where id=:id"),
                @NamedQuery(name = "Course.findById", query = "select c from Course c where c.id=:id")
        }
)
// you cannot simply give multiple @NamedQuery one after the other
// hence do this work around
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @CreationTimestamp // Hibernate annotation -> there will be problems when migrating to another orm
    private LocalDateTime createdOn;

    @UpdateTimestamp // Hibernate annotation -> there will be problems when migrating to another orm
    private LocalDateTime lastUpdated;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    // I don't want to create a separate table for CourseCode and link that here
    // But rather I want fields in CourseCode to be present in the same `course` which this entity is mapped to
    // However I want to maintain CourseCode in a separate class rather than add all its fields here
    // for easier readability
    // So there are no relationships here (unlike the OneToOne relationship between Student and Passport)
    // The solution is to make this field Embedded-Embeddable in both classes
    // @Embedded is used to manage single table with many classes
    @Embedded
    private CourseCode courseCode;

    // We can use Enum instead of Strings. To convert enum <-> string, we can use @Enumerated
    @Enumerated(EnumType.STRING)
    private CourseRating rating;

    // you cannot add default values by this method: https://stackoverflow.com/a/2554796/10582056

    // Workaround to use JPA instead of hibernate for @CreationTimestamp and @UpdateTimestamp:
    // https://stackoverflow.com/a/42367173/10582056

    public Course(String name) {
        this.name = name;
    }

    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * When we execute a @SQLDelete -> the column gets updated instead of deleted
     * <p>
     * But, hibernate does not know about this behaviour, because we are bypassing hibernate with Native SQL.
     * It's like cheating on hibernate. So to inform hibernate, we have to manually set the value ourselves
     * <p>
     * That's where the Lifecycle methods come into play
     */
    @PreRemove
    public void preRemove() {
        isDeleted = true;
    }

    /*
        There are other Lifecycle annotations as well:
            1. @PreRemove -> runs before a removal
            2. @PostRemove -> called after an entity is removed

            3. @PrePersist -> runs before an entity is persisted
            4. @PostPersist -> called after entity is persisted into the database

            5. @PreUpdate -> runs before an entity is updated
            6. @PostUpdate -> runs after an entity is updated

            7. @PostLoad -> runs as soon as entity is retrieved from database and loaded
    */
}
