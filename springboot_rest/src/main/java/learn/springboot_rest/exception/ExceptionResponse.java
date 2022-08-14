package learn.springboot_rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    public Date timeStamp;
    public String message;
    public String details;
}

