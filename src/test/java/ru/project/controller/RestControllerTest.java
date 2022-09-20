package ru.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.project.entity.Company;
import ru.project.entity.Employee;
import ru.project.service.CompanyService;
import ru.project.service.EmployeeService;
import ru.project.utils.CompanyUtils;
import ru.project.utils.EmployeeUtils;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestController.class)
public class RestControllerTest {
    @Autowired
    private RestController restController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        Company company = CompanyUtils.getTestCompany();

        Mockito.when(companyService.findById(0L))
                .thenReturn(Optional.of(company));

    }

    @Test
    public void testOkGetCompanyById() throws Exception {
        this.mockMvc.perform(get("/api/companies/0").contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("get companies")
    public void testOkGetCompanies() throws Exception {
        Mockito.when(companyService.findAll(0, 10))
                .thenReturn(null);
        this.mockMvc.perform(get("/api/companies")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companies").doesNotExist());
    }

    @Test
    @DisplayName("get company's employees")
    public void testOkGetEmployeesByCompanyId() throws Exception {
        Mockito.when(employeeService.findAllByCompanyId(1L, 0, 10))
                .thenReturn(EmployeeUtils.getTestEmployeeList());
        this.mockMvc.perform(get("/api/companies/1/employees")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("get company's employee by id")
    public void testOkGetCompanyEmployee() throws Exception {
        Employee employee = EmployeeUtils.getTestEmployee();
        Mockito.when(employeeService.findById(1L))
                .thenReturn(Optional.of(employee));

        this.mockMvc.perform(get("/api/companies/1/employees/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("add company")
    public void testOkAddCompany() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/companies/company")
                        .content(asJsonString(new Company(null, "firstName4", "lastName4", "email4@mail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("update company")
    @WithMockUser
    public void testOkUpdateCompany() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/companies/{companyId}", 22L)
                        .content(asJsonString(new Company(22L, "firstName22", "test22Website", "email22@mail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("delete company ok")
    public void testOkDeleteCompanyById() throws Exception {
        mockMvc.perform(delete("/api/companies/2"))
                .andExpect(status().isOk());

        Mockito.verify(companyService, Mockito.times(1)).deleteById(2L);
    }

    @Test
    @DisplayName("get employee by id")
    public void testOkFindEmployeeById() throws Exception {
        Employee employee = EmployeeUtils.getTestEmployee();
        Mockito.when(employeeService.findById(1L))
                .thenReturn(Optional.of(employee));

        this.mockMvc.perform(get("/api/employees/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get all employees")
    public void testOkFindAllEmployees() throws Exception {
        List<Employee> employees = EmployeeUtils.getTestEmployeeList();
        Mockito.when(employeeService.findAll(0, 10)).thenReturn(employees);

        this.mockMvc.perform(get("/api/employees")).andExpect(status().isOk());
    }


    @Test
    @DisplayName("update employee")
    @WithMockUser
    public void testUpdateEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/employees/{employeeId}", 22L)
                        .content(asJsonString(new Employee("firstname", "lastname", "8999998787", "test@mail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("add employee")
    @WithMockUser
    public void testAddEmployee() throws Exception {
        Employee employee = EmployeeUtils.getTestEmployee();
        employee.setCompany(CompanyUtils.getTestCompany());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/employees/employee")
                        .content(asJsonString(employee))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete employee ok")
    public void testOkDeleteEmployyeeById() throws Exception {
        mockMvc.perform(delete("/api/employees/2"))
                .andExpect(status().isOk());

        Mockito.verify(employeeService, Mockito.times(1)).deleteById(2L);
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


