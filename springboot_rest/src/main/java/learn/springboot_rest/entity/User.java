package learn.springboot_rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
// There should always be a default no-argument constructor to create BEANs automatically
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    @Size(min = 2, message = "name should have at least 2 chars")
    private String name;

    @Past
    private Date birthDate;

}
