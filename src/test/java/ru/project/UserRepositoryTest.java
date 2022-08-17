package ru.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.project.entity.Authority;
import ru.project.entity.User;
import ru.project.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    private final UserService service;

    public UserRepositoryTest(UserService service) {
        this.service = service;
    }


    @Test
    public void createUser() {
        User user = new User();
        user.setEmail("testEmail");
        user.setPassword("password");
        List<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority();
        authority.setName("ADMIN");
        authorities.add(authority);
        user.setAuthorities(authorities);
        service.add(user);
    }

    @Test
    public void getUser() {
        Long id = 1L;
        Optional<User> mayBeUser = service.get(id);
        if (mayBeUser.isPresent()) {
            System.out.println("SUCCESS" + mayBeUser.get().toString());
        } else {
            throw new RuntimeException("FAILURE: COULDN'T FIND USER WITH ID:" + id);
        }
    }

    @Test
    public void editUser() {
        User editedUser = new User();
        editedUser.setEmail("upd@gmail.com");
        editedUser.setPassword("updPass");
        if (service.update(editedUser)) {
            System.out.println("SUCCESS: USER HAS BEEN UPDATED. UPDATES: " + editedUser.toString());
        } else {
            throw new RuntimeException("FAILURE: COULDN'T UPDATE USER");
        }
    }

    @Test
    public void deleteUser() {
        Long id = 1L;
        Optional<User> mayBeUser = service.get(id);
        mayBeUser.ifPresent(service::delete);
    }
}
