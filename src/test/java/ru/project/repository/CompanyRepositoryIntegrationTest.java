package ru.project.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.project.CompanyEmployeeCrmApplication;
import ru.project.entity.Company;
import ru.project.service.CompanyService;
import ru.project.utils.CompanyUtils;

import java.util.Optional;


@DataJpaTest
@ContextConfiguration(classes = {CompanyService.class, CompanyEmployeeCrmApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Transactional
public class CompanyRepositoryIntegrationTest {

    private final CompanyService companyService;

    private Company testCompany;

    @Autowired
    public CompanyRepositoryIntegrationTest(CompanyService companyService) {
        this.companyService = companyService;
    }

    //    @BeforeAll
//    public void setup() {
//        List<Company> testList = CompanyUtils.getTestCompanyList();
//
//        for (Company company : testList) {
//            companyService.save(company);
//        }
//        List<Company> got = companyService.findAll(0, 10);
//        System.out.println();
//    }
    @BeforeEach
    public void beforeEach() {
        testCompany = CompanyUtils.getTestCompany();

        companyService.save(testCompany);
    }

    @AfterEach
    public void flush() {
        try {
            companyService.deleteById(testCompany.getId());
        } catch (EmptyResultDataAccessException ignored) {

        }
    }


    @Test
    @DisplayName("check company in database")
    public void checkCompanyExists() {
        Long id = testCompany.getId();
        Optional<Company> got = companyService.findById(id);

        Assertions.assertTrue(got.isPresent(), "company should exist in the database");
        Assertions.assertEquals(testCompany.getId(), got.get().getId(), "Ids are not the same");
        Assertions.assertEquals(testCompany.getEmail(), got.get().getEmail(), "Email's are not the same ");
        Assertions.assertEquals(testCompany.getWebsite(), got.get().getWebsite(), "Websites are not the same");

    }

    @Test
    @DisplayName("delete company from database")
    public void deleteCompany() {
//        Company company = new Company(2L, "youtube", "youtube.com", "youtube@gmail.com");

        companyService.deleteById(testCompany.getId());

        Assertions.assertFalse(companyService.existsById(testCompany.getId()), "Company still exists");
    }


    @Test
    @DisplayName("Company update")
    public void update() throws InterruptedException {
        Company company = testCompany;

        company.setName("updatedName");
        company.setEmail("updatedEmail@gmail.com");
        company.setWebsite("updatedWebsite.com");

        companyService.update(company);

//        List<Company> all = companyService.findAll(0, 10);
//        System.out.println("\n\n\n\n\n\n\n\n");
//        System.out.println("Size = " + all.size());
//
//        System.out.println(Arrays.toString(all.stream().map(m -> m.toString() + "\n").toArray()));
//
//        System.out.println("\n\n\n\n\n\n\n\n");


        Optional<Company> got = companyService.findById(company.getId());

//        for (int i = 0; i < 1; i++) {
//            System.out.println(i);
//            if (companyService.findById(company.getId()).isEmpty()) {
//                Thread.sleep(1000);
//            }
//
//        }

        Assertions.assertTrue(got.isPresent(), "company should exist");

        Assertions.assertEquals(company.getName(), got.get().getName(), "Names are not the same");
        Assertions.assertEquals(company.getWebsite(), got.get().getWebsite(), "websites are not the same");
        Assertions.assertEquals(company.getEmail(), got.get().getEmail(), "emails are not the same");

    }

}
