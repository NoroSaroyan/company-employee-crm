package ru.project.service;

import org.springframework.stereotype.Service;
import ru.project.entity.Employee;
import ru.project.repository.EmployeeRepository;


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

}
