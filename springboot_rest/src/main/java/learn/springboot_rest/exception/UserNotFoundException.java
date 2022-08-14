package learn.springboot_rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * `@ResponseStatus` - Marks a method or exception class with the status code and reason that should be returned
 * <p>
 * I can annotate any method with this annotation and alter its default functionality.
 * <p>
 * For example, I can annotate following method:
 * <pre>
 * {@code
 *
 *     @ResponseStatus(HttpStatus.NOT_FOUND)
 *     @GetMapping("/users/{id}")
 *     public User retrieveUser(@PathVariable int id) {
 *
 *         User user = userDAO.getUser(id);
 *
 *         if (null == user) throw new UserNotFoundException(id);
 *         return user;
 *     }
 * }
 * </pre>
 * Now instead of OK message, it will return a 404 NOT FOUND message in its header
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super("id: " + id);
    }
}
