package learn.springframework.restcrud.controller;

import learn.springframework.restcrud.entity.Customer;
import learn.springframework.restcrud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest-api")
public class CustomerController {

    @Autowired
    @Qualifier("customerServiceImpl")
    private CustomerService customerService;

    // listing customers
    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    // get a unique customer
    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable int id) {
        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID: " + id + " is not in the database", new Exception("id == null"));
        }
        return customer;
    }

    // creating a new customer
    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer) {

        /*
         * Internally `customerService.saveCustomer(customer)` -> this method calls
         *       `session.saveOrUpdate(customer)`
         *
         * saveOrUpdate() -> works like this
         * if the id / primary key of object (customer) is 'empty'
         *       (-> setting id of customer object to '0' will indicate this object has no primary key / id)
         * saveOrUpdate() creates new entry in database. Or else updates the value.
         * */

        customer.setId(0);
        customerService.saveCustomer(customer);
        return customer;
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return customer;
    }

    @DeleteMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable int id) {

        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID: " + id + " is not in the database", new Exception("id == null"));
        }

        customerService.deleteCustomer(id);

        String message = String.format("Deleted customer with id: %d", id);
        return message;
    }
}
