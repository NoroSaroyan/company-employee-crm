package ru.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.project.CompanyEmployeeCrmApplication;
import ru.project.entity.User;
import ru.project.service.UserService;
import ru.project.utils.UserUtils;

import java.util.Optional;

@DataJpaTest
@ContextConfiguration(classes = {UserService.class, CompanyEmployeeCrmApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(value = "classpath:application-test.properties")

public class UserRepositoryIntegrationTest {

    private final UserService service;

    @Autowired
    public UserRepositoryIntegrationTest(@Qualifier("userService") UserService service) {
        this.service = service;
    }

    @BeforeAll
    public void setup() {
        User user = UserUtils.getTestUser();
        service.save(user);
    }

    @Test
    public void getUserTest() {
        Long id = 1L;
        Optional<User> got = service.get(id);

        Assertions.assertTrue(got.isPresent(), "User must exist in database");
        Assertions.assertEquals(1L, got.get().getId(), "Ids not the same");
        Assertions.assertEquals("admin@test.com", got.get().getEmail(), "Emails not the same");
        Assertions.assertEquals("pass", got.get().getPassword(), "passwords not the same");

        Assertions.assertEquals(1,got.get().getAuthorities().size(),"Authority list size ");
        Assertions.assertEquals("ADMIN",got.get().getAuthorities().get(0).getName(),"Authority name");
    }



}
