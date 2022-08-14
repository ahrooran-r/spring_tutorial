package learn.jpa_hibernate.repository.inheritence;

import learn.jpa_hibernate.entity.inheritence.Employee;
import learn.jpa_hibernate.entity.inheritence.FullTimeEmployee;
import learn.jpa_hibernate.entity.inheritence.PartTimeEmployee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/*
@Repository
@Transactional
public class EmployeeRepository {

    @PersistenceContext
    EntityManager em;

    public Employee findById(int id) {
        Employee employee = em.find(Employee.class, id);
        return employee;
    }

    public void saveEmployee(Employee employee) {
        if (null == employee.getId()) em.persist(employee);
        else em.merge(employee);
    }

    public List<Employee> findAll() {
        Query query = em.createQuery("select e from Employee e");
        List<Employee> employees = query.getResultList();
        return employees;
    }
}
*/

// This is for @MappedSuperclass
// -> Because there is no Employee entity (see that class, there is no annotation),
// we cannot use Employee as an entity in JPQL
// so here is a different approach

@Repository
@Transactional
public class EmployeeRepository {

    @PersistenceContext
    EntityManager em;

    public Employee findById(int id) {
        Employee employee = em.find(Employee.class, id);
        return employee;
    }

    public void saveEmployee(Employee employee) {
        if (null == employee.getId()) em.persist(employee);
        else em.merge(employee);
    }

    // CANNOT query using JPQL because there is no Employee table or entity to query from
    // So we have to query individually from each subclass
    // see below
    /*
    public List<Employee> findAll() {
        Query query = em.createQuery("select e from Employee e");
        List<Employee> employees = query.getResultList();
        return employees;
    }
    */

    public List<PartTimeEmployee> findAllPartTimeEmployees() {
        Query query = em.createQuery("select pte from PartTimeEmployee pte");
        List<PartTimeEmployee> partTimeEmployees = query.getResultList();
        return partTimeEmployees;
    }

    public List<FullTimeEmployee> findAllFullTimeEmployees() {
        Query query = em.createQuery("select fte from FullTimeEmployee fte");
        List<FullTimeEmployee> fullTimeEmployees = query.getResultList();
        return fullTimeEmployees;
    }

}

