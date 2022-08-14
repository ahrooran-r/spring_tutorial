package learn.springframework.springbootrest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> exceptionHandler(StudentNotFoundException e) {

        // create new StudentErrorResponse
        StudentErrorResponse studentErrorResponse = new StudentErrorResponse();

        // 1. 404-Error
        studentErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        // 2. set error message
        studentErrorResponse.setMessage(e.getMessage());

        studentErrorResponse.setTimeStamp(System.currentTimeMillis());

        // return a response entity
        return new ResponseEntity<>(studentErrorResponse, HttpStatus.NOT_FOUND);
    }

    // add a generic exception handler
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> genericExceptionHandler(Exception e) {

        // create new StudentErrorResponse
        StudentErrorResponse studentErrorResponse = new StudentErrorResponse();

        // 1. 404-Error
        studentErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        // 2. set error message
        studentErrorResponse.setMessage(e.getMessage());

        studentErrorResponse.setTimeStamp(System.currentTimeMillis());

        // return a response entity
        return new ResponseEntity<>(studentErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
