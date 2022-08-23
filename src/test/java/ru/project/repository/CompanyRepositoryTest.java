package ru.project.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.project.CompanyEmployeeCrmApplication;
import ru.project.entity.Company;
import ru.project.entity.Employee;
import ru.project.service.CompanyService;
import ru.project.utils.CompanyUtils;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;


@DataJpaTest
@ContextConfiguration(classes = {CompanyService.class, CompanyEmployeeCrmApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyRepositoryTest {

    private final CompanyService companyService;


    @Autowired
    public CompanyRepositoryTest(CompanyService companyService) {
        this.companyService = companyService;
    }

    @BeforeAll
    public void setup() {
        companyService.save(CompanyUtils.getTestCompany());
    }

    @Test
    @DisplayName("get company from database")
    public void getCompany() {
        Long id = 1L;
        Optional<Company> got = companyService.getById(id);

        Assertions.assertTrue(got.isPresent(), "company should exist in the database");
        Assertions.assertEquals(1L, got.get().getId(), "Ids are not the same");
        Assertions.assertEquals("company@test.com", got.get().getEmail(), "Email's are not the same ");
        Assertions.assertEquals("testCompany.com", got.get().getWebsite(), "Websites are not the same");


    }

    @Test
    @DisplayName("delete company from database")
    public void deleteCompany() {
        Long id = 1L;
        Optional<Company> got = companyService.getById(1L);
        got.ifPresent(companyService::delete);

        Assertions.assertEquals(Optional.empty(), companyService.getById(1L), "User still exists");
    }

    @Test
    @DisplayName("get All companies from database")
    public void getAll() {
        List<Company> companyList = companyService.getAll();

        Assertions.assertFalse(companyList.isEmpty(), "list is null");
    }

    @Test
    @DisplayName("edit company")
    public void edit() {
        Company company;
        Optional<Company> got = companyService.getById(1L);
        if (got.isPresent()) {
            company = got.get();
            Long id = got.get().getId();
            company.setName("googleTest");
            companyService.edit(got.get().getId(), company);

            Optional<Company> updated = companyService.getById(id);
            updated.ifPresent(value -> Assertions.assertEquals
                    ("googleTest", updated.get().getName(),"Company names aren't the same "));
        }
    }

}
