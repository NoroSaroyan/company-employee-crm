package ru.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.project.entity.Company;
import ru.project.repository.CompanyRepository;
import ru.project.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service()
public class CompanyService {
    private final CompanyRepository companyRepository;

    private final EmployeeRepository employeeRepository;


    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public void save(Company company) {
        companyRepository.save(company);
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    public List<Company> findAll(int page, int size) {
        if (size > 10) {
            size = 10;
        }

        Page<Company> pagedResult = companyRepository.findAll(PageRequest.of(page, size));
        if (pagedResult.hasContent()) {
            pagedResult.getContent();
//            return companyRepository.findAll(PageRequest.of(page, size)).stream().toList();
        }
        return new ArrayList<Company>();
    }


    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    public void update(Company company) {
        companyRepository.update(
                company.getId(),
                company.getName(),
                company.getWebsite(),
                company.getEmail());
    }

    public boolean existsById(Long id) {
        return companyRepository.existsById(id);
    }

}
