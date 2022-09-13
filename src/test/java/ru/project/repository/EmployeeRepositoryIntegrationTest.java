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
import ru.project.entity.Employee;
import ru.project.service.CompanyService;
import ru.project.service.EmployeeService;
import ru.project.utils.CompanyUtils;
import ru.project.utils.EmployeeUtils;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ContextConfiguration(classes = {EmployeeService.class, CompanyService.class, CompanyEmployeeCrmApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeRepositoryIntegrationTest {

    private final EmployeeService employeeService;

    private final CompanyService companyService;
    private Employee testEmployee;

    private final Company testCompany = CompanyUtils.getTestCompany();

    @Autowired
    public EmployeeRepositoryIntegrationTest(EmployeeService employeeService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    public void insertEmployee() {
        companyService.save(testCompany);
        testEmployee = EmployeeUtils.getTestEmployee();
        testEmployee.setCompany(companyService.findAll(0, 10).get(0));
        employeeService.save(testEmployee);
    }

    public void insertEmployees() {
        companyService.save(testCompany);
        Company company = companyService.findAll(0, 10).get(0);
        List<Employee> testEmployeeList = EmployeeUtils.getTestEmployeeList();
        testEmployee.setCompany(company);
        testEmployeeList.forEach(employee -> employee.setCompany(company));
        employeeService.saveAll(testEmployeeList);
    }

    @Test
    @DisplayName("check employee in database")
    @Transactional
    public void checkEmployeeExists() {
        insertEmployee();
        Long id = testEmployee.getId();
        Optional<Employee> got = employeeService.findById(id);

        Assertions.assertTrue(got.isPresent(), "employee should exist in the database");
        Assertions.assertEquals(testEmployee.getId(), got.get().getId(), "Ids are not the same");
        Assertions.assertEquals(testEmployee.getName(), got.get().getName(), "Names are not the same");
        Assertions.assertEquals(testEmployee.getSurname(), got.get().getSurname(), "Surnames are not the same");
        Assertions.assertEquals(testEmployee.getEmail(), got.get().getEmail(), "Email's are not the same ");
        Assertions.assertEquals(testEmployee.getPhone_number(), got.get().getPhone_number(), "Phone numbers are not the same");
    }

    @Test
    @DisplayName("delete employee from database")
    @Transactional
    public void deleteOne() {
        insertEmployee();
        employeeService.deleteById(testEmployee.getId());
        Assertions.assertFalse(employeeService.existsById(testCompany.getId()));
    }

    @Test
    @DisplayName("delete manny employee from database")
    @Transactional
    public void deleteMany() {
        insertEmployees();
        List<Employee> list = employeeService.findAll(0, 10);
        int size = list.size();
        Assertions.assertEquals(3, size, "sizes are not equal");

        employeeService.deleteById(list.get(0).getId());
        employeeService.deleteById(list.get(1).getId());
        Assertions.assertFalse(employeeService.existsById(list.get(0).getId()));
        Assertions.assertFalse(employeeService.existsById(list.get(1).getId()));
        Assertions.assertTrue(employeeService.existsById(list.get(2).getId()));
    }

    @Test
    @DisplayName("update employee")
    @Transactional
    public void update() {
        insertEmployee();

        testEmployee.setName("updatedName");
        testEmployee.setEmail("updatedEmail@gmail.com");
        testEmployee.setSurname("updatedSurname");
        testEmployee.setPhone_number("updatedPhoneNumber");
        employeeService.update(testEmployee);

        Optional<Employee> got = employeeService.findById(testEmployee.getId());
        Assertions.assertTrue(got.isPresent(), "employee should exist");
        Assertions.assertEquals(testEmployee.getName(), got.get().getName(), "Names are not the same");
        Assertions.assertEquals(testEmployee.getSurname(), got.get().getSurname(), "surnames are not the same");
        Assertions.assertEquals(testEmployee.getEmail(), got.get().getEmail(), "emails are not the same");
        Assertions.assertEquals(testEmployee.getPhone_number(), got.get().getPhone_number(), "phone numbers are not the same");

    }
}
