package learn.springframework.mvchibernate.service;

import jakarta.transaction.Transactional;
import learn.springframework.mvchibernate.dao.CustomerDAO;
import learn.springframework.mvchibernate.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    @Qualifier("customerDAOImpl")
    private CustomerDAO customerDAO;

    @Override
    @Transactional
    public List<Customer> getCustomers() {
        return customerDAO.getCustomers();
    }

    @Override
    @Transactional
    public void save(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    @Transactional
    public Customer getCustomer(int id) {
        return customerDAO.getCustomer(id);
    }

    @Override
    @Transactional
    public void delete(int id) {
        customerDAO.delete(id);
    }

    @Override
    @Transactional
    public List<Customer> searchCustomers(String value) {
        List<Customer> customerSearchList = customerDAO.searchCustomers(value);
        return customerSearchList;
    }
}
