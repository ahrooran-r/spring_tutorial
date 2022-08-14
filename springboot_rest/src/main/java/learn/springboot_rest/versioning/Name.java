package learn.springboot_rest.versioning;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Name {
    private String firstName;
    private String lastName;
}
