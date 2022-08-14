package learn.jms._5_filter_messages.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Claim implements Serializable {
    private int hospitalId;
    private String doctorName;
    private String doctorType;
    private String insuranceProvider;
    private double claim;
}
