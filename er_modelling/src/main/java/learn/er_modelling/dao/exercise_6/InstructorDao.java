package learn.er_modelling.dao.exercise_6;

import learn.er_modelling.entity.exercise_6.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorDao extends JpaRepository<Officer, Integer> {
}
