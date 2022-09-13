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
        Company company = new Company("testCompany", "testWebsite.com", "test@gmail.com");
        given(companyService.findById(any())).willReturn(Optional.of(company));
        Optional<Company> got = companyService.findById(1L);
        assertTrue(got.isPresent(), "company should not be empty");
        assertThat(got.get().getName()).isEqualTo(("testCompany"));
        assertThat(got.get().getWebsite()).isEqualTo(("testWebsite.com"));
        assertThat(got.get().getEmail()).isEqualTo(("test@gmail.com"));
    }


    @Test
    @DisplayName("find all companies")
    void findAll() {
        List<Company> companies = CompanyUtils.getTestCompanyList();
        List<Company> compareTo = CompanyUtils.getTestCompanyList();

        given(companyService.findAll(anyInt(), anyInt())).
                willReturn(companies);

        List<Company> got = companyService.findAll(2, 10);

        assertThat(got).isNotNull();
        assertEquals(3, got.size(), "list size should be 3");

        for (int i = 0; i < companies.size(); i++) {
            assertEquals(compareTo.get(i).getName(), got.get(i).getName(), "Company names are not the same");
            assertEquals(compareTo.get(i).getEmail(), got.get(i).getEmail(), "Company emails are not the same");
            assertEquals(compareTo.get(i).getWebsite(), got.get(i).getWebsite(), "Company websites are not the same");
        }
    }

    @Test
    @DisplayName("find all companies")
    void findAllInvalidSize() {
        List<Company> companies = CompanyUtils.getTestCompanyList();

        CompanyService service = new CompanyService(companyRepository, null);
        Page<Company> companyPage = new PageImpl<>(companies, PageRequest.of(2, 10), companies.size());

        given(companyRepository.findAll(any(PageRequest.class))).willReturn(companyPage);

        List<Company> got = service.findAll(2, 15);
        then(companyRepository).should().findAll(PageRequest.of(2, 10));

    }

    @Test
    @DisplayName("find all companies")
    void findAll_fixSize() {
        CompanyService service = new CompanyService(companyRepository, null);
        Page<Company> companyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(2, 10), 0);

        given(companyRepository.findAll(any(PageRequest.class))).willReturn(companyPage);

        List<Company> got = service.findAll(2, 11);
        then(companyRepository).should().findAll(PageRequest.of(2, 10));
    }

}
