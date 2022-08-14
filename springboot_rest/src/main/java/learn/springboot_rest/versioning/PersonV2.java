package learn.springboot_rest.versioning;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonV2 {
    private Name name;
}
