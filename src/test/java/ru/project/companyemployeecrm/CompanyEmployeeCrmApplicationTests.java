package ru.project.companyemployeecrm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.project.controller.RestController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(value = "classpath:application-test.properties")
public class CompanyEmployeeCrmApplicationTests {

    @Autowired
    private RestController restController;
    @Test
    void contextLoads() {
        assertThat(restController).isNotNull();
    }

}
