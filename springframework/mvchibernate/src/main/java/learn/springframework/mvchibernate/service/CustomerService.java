package learn.springframework.mvchibernate.service;

import learn.springframework.mvchibernate.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();

    void save(Customer customer);

    Customer getCustomer(int id);

    void delete(int id);

    List<Customer> searchCustomers(String value);

}
