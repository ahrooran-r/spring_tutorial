package learn.springframework.mvchibernate.controller;

import learn.springframework.mvchibernate.entity.Customer;
import learn.springframework.mvchibernate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // inject CustomerServiceImpl into this controller
    @Autowired
    @Qualifier(value = "customerServiceImpl")
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model model) {

        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);

        return "list-customers";
    }

    @GetMapping("/save-customer-form")
    public String showCustomer(Model model) {

        Customer customer = new Customer();
        model.addAttribute("customer", customer);

        return "customer-form";
    }

    @PostMapping("/save-customer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {

        customerService.save(customer);

        // redirecting back to customer list url
        return "redirect:/customer/list";
    }

    @GetMapping("/update-customer-form")
    public String updateCustomer(@RequestParam("customer-id") int id, Model model) {

        Customer customer = customerService.getCustomer(id);
        model.addAttribute("customer", customer);
        return "customer-form";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customer-id") int id) {
        customerService.delete(id);
        return "redirect:/customer/list";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searchValue") String value, Model model) {

        List<Customer> customerSearchList = customerService.searchCustomers(value.toLowerCase());
        model.addAttribute("customers", customerSearchList);
        return "list-customers";
    }
}
