package learn.jpa_hibernate.bean;

import lombok.*;

import java.util.Date;


/**
 * always add a @NoArgsConstructor for every potential bean especially with a RowMapper. Otherwise, BeanPropertyRowMapper won't work
 * <p></p>
 * This is actually 'Person', not 'PersonBean'. But there is another class in Entity with same name.
 * Because that is more important than this, I'm renaming this class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PersonBean {

    private int id;

    private String name;

    private String location;

    private Date birthDate;

    public PersonBean(String name, String location, Date birthDate) {
        this.name = name;
        this.location = location;
        this.birthDate = birthDate;
    }
}
