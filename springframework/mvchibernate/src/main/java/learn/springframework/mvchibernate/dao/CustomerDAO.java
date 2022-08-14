package learn.springframework.mvchibernate.dao;

import learn.springframework.mvchibernate.entity.Customer;

import java.util.List;

public interface CustomerDAO {

    List<Customer> getCustomers();

    void save(Customer customer);

    Customer getCustomer(int id);

    void delete(int id);

    List<Customer> searchCustomers(String value);
}
