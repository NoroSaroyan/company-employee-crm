package ru.project.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.entity.Company;
import ru.project.repository.CompanyRepository;
import ru.project.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        return companyRepository.findAll(PageRequest.of(page, size)).stream().toList();
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public long getCount(){
        return companyRepository.count();
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
