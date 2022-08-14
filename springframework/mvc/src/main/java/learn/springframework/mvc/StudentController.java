package learn.springframework.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/student")
public class StudentController {

    @RequestMapping("/show-form")
    public String showForm(Model model) {

        // create student object
        Student newStudent = new Student();

        // add student object to model
        model.addAttribute("student", newStudent);

        return "student-form";
    }

    @RequestMapping("/process-form")
    public String processForm(@ModelAttribute("student") Student student, Model model) {

        // generate message
        String message = String.format(
                "Student %s %s from %s is registered to %s.\nStudent knows %s",

                student.getFirstName(),
                student.getLastName(),
                student.getCountry(),
                student.getLanguage(),
                Arrays.toString(student.getOs())
        );

        // add message to model
        model.addAttribute("message", message);

        return "student-details";
    }

}
