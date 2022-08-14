package learn.springframework.mvc;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/hello")
// controller level request mapping acts as a parent
// each method level request mapping act as a child
public class HelloWorldController {

    // controller method to show initial form
    // actual mapping will be '/hello/show-form'
    @RequestMapping("/show-form")
    public String showForm() {
        return "helloworld-form";
    }

    // controller method to process the HTML form
    @RequestMapping("/process-form-v1")
    public String processFormV1() {
        return "helloworld";
    }

    /*
     * HttpServletRequest request -> we get the value from browser searchbar to controller using this object
     * Model model -> Save the value in model
     * */
    @RequestMapping("/process-form-v2")
    public String processFormV2(HttpServletRequest request, Model model) {

        // read request parameter from html form
        String name = request.getParameter("studentName");

        // convert data to uppercase
        name = name.toUpperCase();
        // create a message
        String message = String.format("Yo! hello %s", name);

        // add message to model
        // model is like a table: has attributeName and attributeValue
        // other controllers get value by passing in the attributeName
        model.addAttribute("message", message);

        return "helloworld";
    }

    @RequestMapping("/process-form-v3")
    // @RequestParam("studentName") will bind the variable 'studentName' in
    // helloworld-form.jsp to this controller method directly
    public String processFormV3(@RequestParam("studentName") String name, Model model) {

        // create message
        String message = String.format("Yo! hello bro: %s", name.toUpperCase());

        // add message to model
        model.addAttribute("message", message);

        return "helloworld";
    }

}
