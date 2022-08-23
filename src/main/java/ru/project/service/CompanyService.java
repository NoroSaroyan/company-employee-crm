package ru.project.service;

import org.springframework.stereotype.Service;
import ru.project.entity.Company;
import ru.project.entity.Employee;
import ru.project.exception.CompanyNotFoundException;
import ru.project.repository.CompanyRepository;
import ru.project.repository.EmployeeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("companyService")
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public void save(Company company) {
        //employeeRepository.saveAll(company.getEmployees());
        companyRepository.save(company);
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    public List<Company> getAll() {
        Iterable<Company> companies = companyRepository.findAll();
        List<Company> res = new ArrayList<>();
        for (Company company : companies) {
            res.add(company);
        }
        return res;
    }

    public Optional<Company> getById(Long id) {
        return companyRepository.findById(id);
    }
    public void addEmployee(Long companyId, Long employeeId) {
        Optional<Employee> optEmpl = employeeRepository.findById(employeeId);
        Optional<Company> optComp = companyRepository.findById(companyId);
        if (optComp.isPresent() && optEmpl.isPresent()) {
            Company company = optComp.get();
            Employee employee = optEmpl.get();

            company.getEmployees().add(employee);
            companyRepository.save(company);

            employee.setCompany(company);
            employeeRepository.save(employee);
        }
    }

    //Is this ok?
    public List<Long> addEmployees(Long companyId, List<Long> employeeIds) {
        List<Long> error_ids = new ArrayList<>();
        for (Long employeeId : employeeIds) {
            if (employeeRepository.existsById(employeeId)) {
                addEmployee(companyId, employeeId);
            } else {
                error_ids.add(employeeId);
            }
        }
        return error_ids;
    }

    public void edit(Long id, Company updates) {
        Optional<Company> optComp = companyRepository.findById(id);
        if (optComp.isPresent()) {
            companyRepository.save(updates);
        } else {
            throw new RuntimeException();
        }
    }
}
