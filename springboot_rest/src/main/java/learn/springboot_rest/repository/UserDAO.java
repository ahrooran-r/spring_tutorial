package learn.springboot_rest.repository;

import learn.springboot_rest.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// I use @Component instead of @Repository because we do not actually talk to any database here
@Component
public class UserDAO {

    private static final List<User> users = new ArrayList<>();

    static {
        users.addAll(Arrays.asList(
                new User(1, "Joe Mama", new Date()),
                new User(2, "King Kong", new Date()),
                new User(3, "Scooby Doo", new Date())
        ));
    }

    public User getUser(int id) {
        List<User> result = users.stream().filter(user -> user.getId() == id).collect(Collectors.toList());
        if (result.size() == 1) return result.get(0);
        else return null;
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (null == user.getId()) user.setId(users.size() + 1);
        users.add(user);
        return user;
    }

    public User delete(int id) {
        User deletedUser = null;
        for (User user : users) {
            if (user.getId() == id) {
                deletedUser = user;
                users.remove(user);
                break;
            }
        }
        return deletedUser;
    }
}
