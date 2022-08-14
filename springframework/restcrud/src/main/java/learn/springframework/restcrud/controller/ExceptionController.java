package learn.springframework.restcrud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionController {

    Date date = new Date();

    @ExceptionHandler
    public ResponseEntity<CustomerErrorResponse> customerNotFoundExceptionHandler(CustomerNotFoundException customerNotFoundException) {

        CustomerErrorResponse errorResponse = new CustomerErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(customerNotFoundException.getMessage());
        errorResponse.setTimeStamp(date.getTime());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CustomerErrorResponse> genericExceptionHandler(Exception exception) {

        CustomerErrorResponse customerErrorResponse = new CustomerErrorResponse();
        customerErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        customerErrorResponse.setMessage(exception.getMessage());
        customerErrorResponse.setTimeStamp(date.getTime());

        return new ResponseEntity<>(customerErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
