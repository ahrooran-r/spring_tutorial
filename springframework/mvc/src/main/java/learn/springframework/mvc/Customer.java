package learn.springframework.mvc;

import jakarta.validation.constraints.*;
import learn.springframework.mvc.validation.CourseCode;

public class Customer {

    private String firstName;

    // makes this field not empty
    @NotNull(message = "is required")
    // makes this field having a size of minimum=2 and maximum=20
    @Size(min = 2, max = 20, message = "size not enough")
    private String lastName;

    @NotNull(message = "is required")
    @Min(value = 0, message = "must be >=0")
    @Max(value = 10, message = "must be <=10")
    private Integer freePasses;
    /*
     * to add own custom message
     * 1. create messages.properties file
     * 2. add messages.properties file to dispatcher-servlet.xml
     * 3. add this line to messages.properties: typeMismatch.customer.freePasses = invalid number
     *                                     error_type.spring_model_attribute_name.field name = my_custom_message
     * */

    @Pattern(regexp = "^[a-zA-Z0-9]{5}", message = "only 5 chars/digits")
    private String postalCode;

    @NotNull(message = "is required")
    //@Pattern(regexp = "^AHR([a-zA-Z0-9]{5})", message = "enter correct code")
    @CourseCode(value = "AHR", message = "input not according to constraints")
    private String courseCode;

    public Customer() {
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getFreePasses() {
        return freePasses;
    }

    public void setFreePasses(Integer freePasses) {
        this.freePasses = freePasses;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
