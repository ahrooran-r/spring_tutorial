package learn.jpa_hibernate.repository.basics;

import learn.jpa_hibernate.bean.PersonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
// if something talks with database -> use @Repository
public class PersonJdbcDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // select * from person
    public List<PersonBean> findAll() {
        return jdbcTemplate.query(
                "select * from person",
                new BeanPropertyRowMapper<>(PersonBean.class)
        );
    }

    // "select * from person where id = ?"
    public PersonBean findById(int id) {
        return jdbcTemplate.queryForObject(
                "select * from person where id=?",
                new BeanPropertyRowMapper<>(PersonBean.class),
                id
        );
        // We can add other params as well ->
        // "select * from person where id=? and name=?", ...... , id, name
    }

    /**
     * using custom row mapper
     *
     * @see #findById(int)
     * @see PersonRowMapper
     */
    public PersonBean findByIdWithPersonRowMapper(int id) {
        return jdbcTemplate.queryForObject(
                "select * from person where id=?",
                new PersonRowMapper(),
                id
        );
        // We can add other params as well ->
        // "select * from person where id=? and name=?", ...... , id, name
    }


    /**
     * "delete from person where id = ?"
     */
    public int deleteById(int id) {
        return jdbcTemplate.update(
                "delete from person where id=?",
                id
        );
    }

    /**
     * "insert into person(col1, col2, col3, col4) value(?, ?, ?, ?)"
     *
     * @see #deleteById(int)
     */
    public int insert(PersonBean personBean) {
        return jdbcTemplate.update(
                "insert into person(name, location, birth_date) value (?, ?, ?)",
                personBean.getName(),
                personBean.getLocation(),
                new Timestamp(personBean.getBirthDate().getTime())
        );
        // no need to give id -> because table auto_increment
    }

    public int update(PersonBean personBean) {
        return jdbcTemplate.update(
                "update person set name=?, location=?, birth_date=? where id=?",
                personBean.getName(),
                personBean.getLocation(),
                new Timestamp(personBean.getBirthDate().getTime()),
                personBean.getId()
        );
        // no need to give id -> because table auto_increment
    }

    /**
     * This is a custom row mapper.
     * This is defined as inner class to make it visible to only this class
     */
    class PersonRowMapper implements RowMapper<PersonBean> {

        /**
         * this gives me 2 options.
         * Use table column names and use row number.
         * Using these 2, we can pinpoint a specific cell in table
         */
        @Override
        public PersonBean mapRow(ResultSet rs, int rowNum) throws SQLException {

            PersonBean personBean = new PersonBean(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getTimestamp("birth_date")
            );

            return personBean;
        }
    }
}
