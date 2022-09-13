package ru.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.project.CompanyEmployeeCrmApplication;
import ru.project.entity.Company;
import ru.project.service.CompanyService;
import ru.project.utils.CompanyUtils;

import java.util.Optional;


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

    public void insertCompany() {
        testCompany = CompanyUtils.getTestCompany();
        companyService.save(testCompany);
    }

    @Test
    @DisplayName("check company in database")
    @Transactional
    public void checkCompanyExists() {
        insertCompany();
        Long id = testCompany.getId();
        Optional<Company> got = companyService.findById(id);

        Assertions.assertTrue(got.isPresent(), "company should exist in the database");
        Assertions.assertEquals(testCompany.getId(), got.get().getId(), "Ids are not the same");
        Assertions.assertEquals(testCompany.getEmail(), got.get().getEmail(), "Email's are not the same ");
        Assertions.assertEquals(testCompany.getWebsite(), got.get().getWebsite(), "Websites are not the same");
    }

    @Test
    @DisplayName("delete company from database")
    @Transactional
    public void deleteCompany() {
        insertCompany();
        companyService.deleteById(testCompany.getId());
        Assertions.assertFalse(companyService.existsById(testCompany.getId()), "Company still exists");
    }

    @Test
    @DisplayName("Company update")
    @Transactional
    public void update() {
        insertCompany();

        testCompany.setName("updatedName");
        testCompany.setEmail("updatedEmail@gmail.com");
        testCompany.setWebsite("updatedWebsite.com");
        companyService.update(testCompany);

        Optional<Company> got = companyService.findById(testCompany.getId());
        Assertions.assertTrue(got.isPresent(), "company should exist");
        Assertions.assertEquals(testCompany.getName(), got.get().getName(), "Names are not the same");
        Assertions.assertEquals(testCompany.getWebsite(), got.get().getWebsite(), "websites are not the same");
        Assertions.assertEquals(testCompany.getEmail(), got.get().getEmail(), "emails are not the same");

    }

}
