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
import ru.project.service.CompanyService;
import ru.project.utils.CompanyUtils;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;


@DataJpaTest
@ContextConfiguration(classes = {CompanyService.class, CompanyEmployeeCrmApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyRepositoryIntegrationTest {

    private final CompanyService companyService;

    private Company testCompany;

    @Autowired
    public CompanyRepositoryIntegrationTest(CompanyService companyService) {
        this.companyService = companyService;
    }

    @BeforeAll
    public void setup() {
        testCompany = CompanyUtils.getTestCompany();
        companyService.save(testCompany);
    }

    @Test
    @DisplayName("get company from database")
    public void getCompany() {
        Long id = 1L;
        Optional<Company> got = companyService.findById(id);

        Assertions.assertTrue(got.isPresent(), "company should exist in the database");
        Assertions.assertEquals(1L, got.get().getId(), "Ids are not the same");
        Assertions.assertEquals(testCompany.getEmail(), got.get().getEmail(), "Email's are not the same ");
        Assertions.assertEquals("testCompany.com", got.get().getWebsite(), "Websites are not the same");

    }

    @Test
    @DisplayName("delete company from database")
    public void deleteCompany() {
        Long id = testCompany.getId();
        companyService.deleteById(id);

        Assertions.assertTrue(companyService.existsById(1L), "Company still exists");
    }

    @Test
    @DisplayName("get All companies from database")
    public void getAll() {
        List<Company> companyList = companyService.findAll(2, 10);
        Assertions.assertFalse(companyList.isEmpty(), "list is null");
    }

    @Test
    @DisplayName("Company update")
    public void update() {
        Company company = new Company(2L,"amazon","amazon.com","amazon@gmail.com");

        testCompany.setName(company.getName());
        testCompany.setWebsite(company.getWebsite());
        testCompany.setEmail(company.getEmail());
        companyService.update(testCompany);

        Optional<Company> got = companyService.findById(testCompany.getId());

        if (got.isPresent()) {
            Assertions.assertEquals(company.getName(), got.get().getName(), "Names are not the same");
            Assertions.assertEquals(company.getWebsite(), got.get().getWebsite(), "websites are not the same");
            Assertions.assertEquals(company.getEmail(), got.get().getEmail(), "emails are not the same");
        }

    }

}
