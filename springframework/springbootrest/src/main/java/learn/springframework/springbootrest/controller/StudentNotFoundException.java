package learn.springframework.springbootrest.controller;

public class StudentNotFoundException extends RuntimeException {

    // create constructors from super class
    final static String MESSAGE = "Student Not found";
    final static Throwable CAUSE = new IndexOutOfBoundsException();


    public StudentNotFoundException() {
        super(MESSAGE, CAUSE);
    }

    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFoundException(Throwable cause) {
        super(cause);
    }
}
