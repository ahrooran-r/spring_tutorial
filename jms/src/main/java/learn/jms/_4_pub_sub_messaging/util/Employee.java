package learn.jms._4_pub_sub_messaging.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Employee implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String designation;
    private String phone;
}
