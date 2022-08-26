package ru.project.companyemployeecrm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(value = "classpath:application-test.properties")
public class CompanyEmployeeCrmApplicationTests {

	@Test
	void contextLoads() {
	}

}
