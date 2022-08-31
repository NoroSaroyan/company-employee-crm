package ru.project.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.project.entity.Company;
import ru.project.entity.Employee;
import ru.project.repository.EmployeeRepository;
import ru.project.utils.CompanyUtils;
import ru.project.utils.EmployeeUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@DisplayName("Employee Service Test")
@ExtendWith(MockitoExtension.class)
public class EmployeesServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeService employeeService;

    @Test
    @DisplayName("get by id return if successful")
    void getByIdFound(){

        Employee employee = EmployeeUtils.getTestEmployee();
        Company set = CompanyUtils.getTestCompany();
        employee.setCompany(set);

        given(employeeService.findById(any())).willReturn(Optional.of(employee));

        Optional<Employee> got = employeeService.findById(1L);

        assertTrue(got.isPresent(),"Employee should exist");

        assertThat(got.get().getName()).isEqualTo("John");
        assertThat(got.get().getSurname()).isEqualTo("Doe");
        assertThat(got.get().getEmail()).isEqualTo("JohnDoe@gmail.com");
        assertThat(got.get().getPhone_number()).isEqualTo("+79001234567");

        assertThat(got.get().getCompany().getName()).isEqualTo(set.getName());
        assertThat(got.get().getCompany().getWebsite()).isEqualTo(set.getWebsite());
        assertThat(got.get().getCompany().getEmail()).isEqualTo(set.getEmail());
    }

    @Test
    @DisplayName("find all employees")
    void findAll() {
        List<Company> companies = CompanyUtils.getTestCompanyList();
        List<Employee> employees = EmployeeUtils.getTestEmployeeList();
        List<Employee> compareTo = EmployeeUtils.getTestEmployeeList();

        for (int i = 0; i < employees.size(); i++) {
            employees.get(i).setCompany(companies.get(i));
            compareTo.get(i).setCompany(companies.get(i));
        }

        given(employeeService.findAll(anyInt(), anyInt())).willReturn(employees);

        List<Employee>got = employeeService.findAll(0, 10);
        assertThat(got).isNotNull();
        assertEquals(3, got.size(), "list size should be 3");

        for (int i = 0; i < employees.size(); i++) {
            assertEquals(compareTo.get(i).getName(), got.get(i).getName(), "Employee names are not the same");
            assertEquals(compareTo.get(i).getSurname(), got.get(i).getSurname(), "Employee Surnames are not the same");
            assertEquals(compareTo.get(i).getEmail(), got.get(i).getEmail(), "Employee emails are not the same");
            assertEquals(compareTo.get(i).getPhone_number(), got.get(i).getPhone_number(), "Employee Phone numbers are not the same");

            assertEquals(compareTo.get(i).getCompany().getName(), got.get(i).getCompany().getName(),
                    "Company names should be the same");
            assertEquals(compareTo.get(i).getCompany().getWebsite(), got.get(i).getCompany().getWebsite(),
                    "Company websites should be the same");
            assertEquals(compareTo.get(i).getCompany().getName(), got.get(i).getCompany().getName(),
                    "Company names should be the same");
            assertEquals(compareTo.get(i).getCompany().getEmail(), got.get(i).getCompany().getEmail(),
                    "Company emails should be the same");
        }

    }
}
