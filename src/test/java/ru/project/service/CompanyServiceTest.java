package ru.project.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.project.entity.Company;
import ru.project.entity.Employee;
import ru.project.repository.CompanyRepository;
import ru.project.utils.CompanyUtils;
import ru.project.utils.EmployeeUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@DisplayName("test company service")
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("can get existing company")
    void getByIdFound() {

        Employee employee = EmployeeUtils.getTestEmployee();
        Company company = new Company("testCompany", "testWebsite.com", "test@gmail.com");
        company.setEmployees(List.of(employee));

        given(companyService.findById(any())).willReturn(Optional.of(company));

        Optional<Company> got = companyService.findById(1L);

        assertTrue(got.isPresent(), "company should not be empty");
        assertThat(got.get().getName()).isEqualTo(("testCompany"));
        assertThat(got.get().getWebsite()).isEqualTo(("testWebsite.com"));
        assertThat(got.get().getEmail()).isEqualTo(("test@gmail.com"));
        assertThat(got.get().getEmployees()).isNotNull();

        assertEquals(1, got.get().getEmployees().size(), "employees list size are not the same");

        assertEquals(employee.getName(), got.get().getEmployees().get(0).getName(),
                "employee's names are not the same");
        assertEquals(employee.getSurname(), got.get().getEmployees().get(0).getSurname(),
                "employee's surnames are not the same");

        assertEquals(employee.getEmail(), got.get().getEmployees().get(0).getEmail(),
                "employee's emails are not the same");

        assertEquals(employee.getPhone_number(), got.get().getEmployees().get(0).getPhone_number(),
                "employee's phone numbers are not the same");
    }


    @Test
    @DisplayName("find all companies")
    void findAll() {
        List<Company> companies = CompanyUtils.getTestCompanyList();
        List<Employee> employees = EmployeeUtils.getTestEmployeeList();

        List<Company> compareTo = CompanyUtils.getTestCompanyList();

        for (int i = 0; i < companies.size(); i++) {
            companies.get(i).setEmployees(List.of(employees.get(i)));
            compareTo.get(i).setEmployees(List.of(employees.get(i)));
        }


        given(companyService.findAll(anyInt(), anyInt())).
                willReturn(companies);

        List<Company> got = companyService.findAll(2, 10);

        assertThat(got).isNotNull();
        assertEquals(3, got.size(), "list size should be 3");
        assertEquals(1, got.get(0).getEmployees().size(), "Companies should have 1 employee");

        for (int i = 0; i < companies.size(); i++) {
            assertEquals(compareTo.get(i).getName(), got.get(i).getName(), "Company names are not the same");
            assertEquals(compareTo.get(i).getEmail(), got.get(i).getEmail(), "Company emails are not the same");
            assertEquals(compareTo.get(i).getWebsite(), got.get(i).getWebsite(), "Company websites are not the same");
            for (int j = 0; j < 1; j++) {
                assertEquals(got.get(i).getEmployees().get(j), compareTo.get(i).getEmployees().get(j));
            }
        }
    }

    @Test
    @DisplayName("find all companies")
    void findAll2() {
        List<Company> companies = CompanyUtils.getTestCompanyList();
        List<Employee> employees = EmployeeUtils.getTestEmployeeList();

        for (int i = 0; i < companies.size(); i++) {
            companies.get(i).setEmployees(List.of(employees.get(i)));
        }


        CompanyService service = new CompanyService(companyRepository, null);
        Page<Company> companyPage = new PageImpl<>(companies, PageRequest.of(2, 10), companies.size());

        given(companyRepository.findAll(any(PageRequest.class))).willReturn(companyPage);

        List<Company> got = service.findAll(2, 10);
        then(companyRepository).should().findAll(PageRequest.of(2, 10));

    }

    @Test
    @DisplayName("find all companies")
    void findAll_checkThatSizeLessThan10() {
        CompanyService service = new CompanyService(companyRepository, null);
        Page<Company> companyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(2, 10), 0);

        given(companyRepository.findAll(any(PageRequest.class))).willReturn(companyPage);

        List<Company> got = service.findAll(2, 11);
        then(companyRepository).should().findAll(PageRequest.of(2, 10));
    }

}
