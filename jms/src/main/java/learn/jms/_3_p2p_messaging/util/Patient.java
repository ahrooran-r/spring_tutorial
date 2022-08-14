package learn.jms._3_p2p_messaging.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Patient implements Serializable {

    private int id;
    private String name;
    private String insuranceProvider;
    private Double copay;
    private Double amountToBePaid;
}
