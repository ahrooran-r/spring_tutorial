package learn.springframework.springboot.dao;

import learn.springframework.springboot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // that's it no need to write anything
}
