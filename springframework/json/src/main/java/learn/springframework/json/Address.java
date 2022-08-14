package learn.springframework.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private int houseNumber;
    private String street;
    private String area;
    private String city;
    private String district;
    private String province;
}
