package learn.springboot_rest.controller;

import learn.springboot_rest.entity.User;
import learn.springboot_rest.exception.UserNotFoundException;
import learn.springboot_rest.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserDAO userDAO;

    @Autowired
    private MessageSource messageSource;

    // Constructor injection is preferred over field injection -> https://stackoverflow.com/q/39890849/10582056
    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDAO.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {

        User user = userDAO.getUser(id);

        if (null == user) throw new UserNotFoundException(id);
        return user;
    }

    /**
     * `@RequestBody` converts whatever I pass into the body of the request into User object
     * <p>
     * Note that in the web browser everything is passed as JSON objects.
     * So to automatically tell Jackson to convert the JSON object into `User` object, it is necessary to
     * put `@RequestBody`
     */
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        user = userDAO.save(user);

        // Best practice is to return a status called `created` along with URI to the newly created resource
        // URI would look like this: `/users/4`
        // So we need to create that first...
        // `ServletUriComponentsBuilder` class offers different methods for that
        // It can capture current request URI -> "/users"
        // Now we just need to add `4` to it -> `path` method does it

        // https://stackoverflow.com/a/42650241/10582056

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        // Now we need to send this along with a `CREATED` status code
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable int id) {

        User user = userDAO.delete(id);

        if (null == user) throw new UserNotFoundException(id);
        return user;
    }

    // simplified internationalization
    @GetMapping("/user-inter")
    public String sayHello() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }
}
