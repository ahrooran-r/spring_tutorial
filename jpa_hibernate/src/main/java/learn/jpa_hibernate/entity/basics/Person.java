package learn.jpa_hibernate.entity.basics;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @ Entity name is used to refer your entity throughout the application, notably in HQL queries
 * <p>
 * @ Table is the actual DB table name
 */
@Getter
@Setter
@Entity(name = "Person")
@Table(name = "person")
@NamedQuery(name = "find_all", query = "select p from Person p")
// here `Person` refers to `name` given in @Entity annotation -> @Entity(name = "Person")
// note that JPQL is slightly different from MySQL
public class Person {

    // For data conversions between MySQL and Java:
    // https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-type-conversions.html

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // This works when `auto_increment` in mysql is enabled: https://stackoverflow.com/q/20603638/10582056
    @Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
    // Only access `id` field through constructor -> hence AccessLevel.NONE
    @Setter(AccessLevel.NONE)
    private int id;

    // here both name in class and actual column name match
    // so no need to manually specify
    // but in case where table names are different, then we can give some custom name in class
    // and use @Column to map the attribute to the table
    // for example
    // @Column(name = "name")
    // private String personName;
    // This allows attribute `personName` to map with table column `name`
    @Column(name = "name")
    private String name;

    // length -> similar to length given within () like varchar(100) in mysql
    @Column(name = "location", length = 100)
    private String location;

    // nullable -> means birthDate cannot have null value -> similar to `not null` in mysql
    // unique -> similar to `unique` in mysql
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    /**
     * Every entity should have a no-args constructor
     */
    public Person() {
    }

    public Person(String name, String location, Date birthDate) {
        this.name = name;
        this.location = location;
        this.birthDate = birthDate;
    }

    public Person(int id, String name, String location, Date birthDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.birthDate = birthDate;
    }

    public String toString() {
        return "Person(id=" + this.id + ", name=" + this.name + ", location=" + this.location + ", birthDate=" + this.birthDate + ")";
    }
}
