package learn.springframework.mvc;

import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    /*
     * pre-process every String data
     * Remove every leading and trailing white space
     * emptyAsNull=true -> if string has only white space, trim it to null
     * */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping("/show-form")
    public String showForm(Model model) {

        Customer newCustomer = new Customer();
        model.addAttribute("customer", newCustomer);

        return "customer-form";
    }

    @RequestMapping("/process-form")
    /*
     * When performing Spring MVC validation, the location of the BindingResult parameter is very important.
     * In the method signature, the BindingResult parameter must immediately after the model attribute.
     * If you place it in any other location, Spring MVC validation will not work as desired.
     * In fact, your validation rules will be ignored.
     * */
    public String processForm(
            // @Valid -> error checking
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "customer-form";
        } else {
            return "customer-details";
        }
    }
}
