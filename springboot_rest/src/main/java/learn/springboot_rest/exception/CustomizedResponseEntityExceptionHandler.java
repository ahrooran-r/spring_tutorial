package learn.springboot_rest.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * `ResponseEntityExceptionHandler` is
 * a convenient base class for @ControllerAdvice classes that wish to provide centralized exception
 * handling across all @RequestMapping methods through @ExceptionHandler methods.
 */
@ControllerAdvice
// Specialization of @Component for classes that declare @ExceptionHandler ... etc.
// methods to be shared across multiple @Controller classes.
@RestController
// This is also a Rest Controller
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    // @ExceptionHandler -> Annotation for handling exceptions in specific handler classes and/or handler methods.
    // Any exception of the form `Exception.class` will be handled by this method
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        // Custom response I defined
        ExceptionResponse response = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

        // Custom response I defined
        ExceptionResponse response = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // ex.getBindingResult() -> contains details about what went wrong in this exception
        ExceptionResponse response = new ExceptionResponse(new Date(), "Validation failed", ex.getBindingResult().toString());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
