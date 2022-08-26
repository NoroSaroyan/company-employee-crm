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
import ru.project.repository.CompanyRepository;
import ru.project.utils.CompanyUtils;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
        given(companyService.findById(any()))
                .willReturn(Optional.of(
                        new Company(
                                 "testCompany", "testWebsite.com", "test@gmail.com")));

        Optional<Company> got = companyService.findById(1L);
        assertTrue(got.isPresent(), "company should not be empty");
        Company company = got.get();
        assertThat(company.getName()).isEqualTo(("testCompany"));
        assertThat(company.getWebsite()).isEqualTo(("testWebsite.com"));
        assertThat(company.getEmail()).isEqualTo(("test@gmail.com"));
    }


    @Test
    @DisplayName("find all companies")
    void findAll() {

        given(companyService.findAll(anyInt(), anyInt())).
                willReturn(CompanyUtils.getTestCompanyList());

        List<Company> utils = CompanyUtils.getTestCompanyList();
        List<Company> got = companyService.findAll(2, 10);

        assertThat(got).isNotNull();
        assertEquals(3, got.size(), "list size should be 3");

        assertEquals(utils.get(0).getName(), got.get(0).getName(), "Company names are not the same");
        assertEquals(utils.get(0).getEmail(), got.get(0).getEmail(), "Company emails are not the same");
        assertEquals(utils.get(0).getWebsite(), got.get(0).getWebsite(), "Company websites are not the same");

        assertEquals(utils.get(1).getName(), got.get(1).getName(), "Company names are not the same");
        assertEquals(utils.get(1).getEmail(), got.get(1).getEmail(), "Company emails are not the same");
        assertEquals(utils.get(1).getWebsite(), got.get(1).getWebsite(), "Company emails are not the same");

        assertEquals(utils.get(2).getName(), got.get(2).getName(), "Company names are not the same");
        assertEquals(utils.get(2).getEmail(), got.get(2).getEmail(), "Company emails are not the same");
        assertEquals(utils.get(2).getWebsite(), got.get(2).getWebsite(), "Company emails are not the same");

    }

    @Test
    @DisplayName("find all companies")
    void findAll2() {
        List<Company> companies = CompanyUtils.getTestCompanyList();
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
        Page<Company> companyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(2, 10),0);

        given(companyRepository.findAll(any(PageRequest.class))).willReturn(companyPage);

        List<Company> got = service.findAll(2, 11);
        then(companyRepository).should().findAll(PageRequest.of(2, 10));
    }

}
