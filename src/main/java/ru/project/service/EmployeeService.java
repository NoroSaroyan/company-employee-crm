package ru.project.service;

import org.springframework.stereotype.Service;
import ru.project.entity.Company;
import ru.project.entity.Employee;
import ru.project.repository.EmployeeRepository;

import java.util.Optional;

@Service("employeeService")
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final CompanyService companyService;

    public EmployeeService(EmployeeRepository employeeRepository, CompanyService companyService) {
        this.employeeRepository = employeeRepository;
        this.companyService = companyService;
    }

    public void save(Employee employee){
        employeeRepository.save(employee);
    }

    public void updateName(Long id, String name){
        Optional<Employee> optEmp = employeeRepository.findById(id);
        if (optEmp.isPresent()){
            Employee employee = optEmp.get();
            employee.setName(name);
            employeeRepository.save(employee);
        }
    }
    public void updateSurname(Long id, String surname){
        Optional<Employee> optEmp = employeeRepository.findById(id);
        if (optEmp.isPresent()){
            Employee employee = optEmp.get();
            employee.setSurname(surname);
            employeeRepository.save(employee);
        }
    }
    public void updateEmail(Long id, String email){
        Optional<Employee> optEmp = employeeRepository.findById(id);
        if (optEmp.isPresent()){
            Employee employee = optEmp.get();
            employee.setEmail(email);
            employeeRepository.save(employee);
        }
    }
    public void updatePhoneNumber(Long id, String phoneNumber){
        Optional<Employee> optEmp = employeeRepository.findById(id);
        if (optEmp.isPresent()){
            Employee employee = optEmp.get();
            employee.setPhone_number(phoneNumber);
            employeeRepository.save(employee);
        }
    }
    public void updateCompany(Long employeeId, Long companyId){
        companyService.addEmployee(companyId,employeeId);
    }

}
