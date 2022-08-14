package learn.springframework.springboot.service;

import learn.springframework.springboot.Exception.EmployeeNotFoundException;
import learn.springframework.springboot.dao.EmployeeRepository;
import learn.springframework.springboot.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeRepositoryServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(int id) {

        // Optional is introduced in Java 8. Now you don't have to manually check for null values
        Optional<Employee> result = employeeRepository.findById(id);

        if (result.isEmpty()) throw new EmployeeNotFoundException("Employee with id: " + id + " not found in database");
        Employee employee = result.get();
        return employee;
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }
}
