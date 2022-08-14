package learn.springframework.springboot.controller;

import lombok.Data;

@Data
public class EmployeeExceptionResponse {
    private int status;
    private String message;
    private String timeStamp;
}
