package ru.project.service;

import org.springframework.data.jpa.repository.Modifying;
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
        companyRepository.save(company);
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }

    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    //TODO think of how should edit work.
    public List<Company> getAll() {
        Iterable<Company> companies = companyRepository.findAll();
        List<Company> res = new ArrayList<>();
        for (Company company : companies) {
            res.add(company);
        }
        return res;
    }

    public Optional<Company> getById(Long id){
        return companyRepository.findById(id);

    }

    /*updating companies name.
    name: company's new name
    id: company's id which name should be updated
     */
    @Modifying
    public void updateName(Long id, String name) {
        Optional<Company> optComp = companyRepository.findById(id);
        if (optComp.isPresent()) {
            Company toBeUpdated = optComp.get();
            toBeUpdated.setName(name);
            companyRepository.save(toBeUpdated);
        } else {
            throw new RuntimeException();
        }
    }

    @Modifying
    public void updateWebsite(Long id, String website) {
        Optional<Company> optComp = companyRepository.findById(id);
        if (optComp.isPresent()) {
            Company toBeUpdated = optComp.get();
            toBeUpdated.setWebsite(website);
            companyRepository.save(toBeUpdated);
        } else {
            throw new RuntimeException();
        }
    }

    @Modifying
//    @Query("update companies c set c.email = :email where c.id = :id")
    public void updateEmail(Long id, String email) {
        Optional<Company> optComp = companyRepository.findById(id);
        if (optComp.isPresent()) {
            Company toBeUpdated = optComp.get();
            toBeUpdated.setEmail(email);
            companyRepository.save(toBeUpdated);
        } else {
            throw new RuntimeException();
        }
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
