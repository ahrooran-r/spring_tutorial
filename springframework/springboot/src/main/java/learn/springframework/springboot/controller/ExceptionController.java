package learn.springframework.springboot.controller;

import learn.springframework.springboot.Exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<EmployeeExceptionResponse> employeeNotFoundExceptionHandler(EmployeeNotFoundException employeeNotFoundException) {

        EmployeeExceptionResponse response = new EmployeeExceptionResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(employeeNotFoundException.getMessage());

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        response.setTimeStamp(timeStamp);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeExceptionResponse> badRequestException(Exception exception) {
        EmployeeExceptionResponse response = new EmployeeExceptionResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        response.setTimeStamp(timeStamp);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
