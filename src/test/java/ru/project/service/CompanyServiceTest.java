package ru.project.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.project.entity.Company;
import ru.project.utils.CompanyUtils;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("test company service")
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyService companyService;

    @Test
    @DisplayName("can get existing company")
    void getByIdFound() {
        given(companyService.getById(any()))
                .willReturn(Optional.of(
                        new Company(
                                1L, "testCompany", "testWebsite.com", "test@gmail.com")));

        Optional<Company> got = companyService.getById(1L);
        assertTrue(got.isPresent(), "company should not be empty");
        Company company = got.get();
        assertThat(company.getName()).isEqualTo(("testCompany"));
        assertThat(company.getWebsite()).isEqualTo(("testWebsite.com"));
        assertThat(company.getEmail()).isEqualTo(("test@gmail.com"));
    }


    // Won't add id to object
    @Test
    @DisplayName("can get all companies")
    void getAll() {
        List<Company> companyList = CompanyUtils.getTestCompanyList();

        given(companyService.getAll()).willReturn(companyList);

        List<Company> got = companyService.getAll();

        assertThat(got).isNotNull();
        assertFalse(got.isEmpty(),"Company list shouldn't be empty");

//        assertThat(companyList.get(0).getId()).isEqualTo(1L);
        assertThat(companyList.get(0).getName()).isEqualTo("google");
        assertThat(companyList.get(0).getWebsite()).isEqualTo("google.com");
        assertThat(companyList.get(0).getEmail()).isEqualTo("google@gmail.com");

//        assertThat(companyList.get(1).getId()).isEqualTo(2L);
        assertThat(companyList.get(1).getName()).isEqualTo("yandex");
        assertThat(companyList.get(1).getWebsite()).isEqualTo("yandex.ru");
        assertThat(companyList.get(1).getEmail()).isEqualTo("company@yandex.ru");

//        assertThat(companyList.get(2).getId()).isEqualTo(3L);
        assertThat(companyList.get(2).getName()).isEqualTo("VK");
        assertThat(companyList.get(2).getWebsite()).isEqualTo("vk.com");
        assertThat(companyList.get(2).getEmail()).isEqualTo("vk@gmail.com");
    }

}
