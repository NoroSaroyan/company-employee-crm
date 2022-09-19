package ru.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.project.entity.Company;
import ru.project.service.CompanyService;
import ru.project.service.EmployeeService;
import ru.project.utils.CompanyUtils;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestController.class)
//@SpringBootTest
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
    public void testOkGetCompanies() throws Exception {
        Mockito.when(companyService.findAll(0, 10))
                .thenReturn(null);

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(MockMvcResultMatchers.content());
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        this.mockMvc.perform(get("/api/companies")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companies").doesNotExist());
    }
}


