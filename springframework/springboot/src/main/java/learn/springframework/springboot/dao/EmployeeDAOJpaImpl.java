/*
 *              Native Hibernate methods vs JPA methods
 *              ----------------------------------------
 * 1. Create => session.save(object) vs entityManager.persist(object)
 * 2. retrieve entity by ID => session.get(Object.class, id) vs entityManager.find(Object.class, id)
 * 3. retrieve list of entities => session.createQuery(...) vs entityManager.createQuery(...)
 * 4. Save or update => session.saveOrUpdate(object) vs entityManager.merge(object)
 * 5. Delete => session.delete(object) vs entityManager.remove(object)
 * */


package learn.springframework.springboot.dao;

import learn.springframework.springboot.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Employee> getEmployees() {

        TypedQuery<Employee> query = entityManager.createQuery("from Employee", Employee.class);
        List<Employee> employees = query.getResultList();

        return employees;
    }

    @Override
    public Employee getEmployee(int id) {
        Employee employee = entityManager.find(Employee.class, id);
        return employee;
    }

    @Override
    public void saveEmployee(Employee employee) {

        // save or update the employee
        Employee dbEmployee = entityManager.merge(employee);

        // update with id from db -> so we can get generated id
        employee.setId(dbEmployee.getId());
    }

    @Override
    public void deleteEmployee(int id) {
        Query query = entityManager.createQuery("delete from Employee where id=:employeeId");
        query.setParameter("employeeId", id);
        query.executeUpdate();
    }
}
