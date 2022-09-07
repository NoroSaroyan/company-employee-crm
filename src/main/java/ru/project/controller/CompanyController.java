package ru.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.project.entity.Company;
import ru.project.entity.Employee;
import ru.project.service.CompanyService;
import ru.project.service.EmployeeService;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    private final EmployeeService employeeService;

    @Autowired
    public CompanyController(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String getAllCompanies(Model model, int size, int page) {
        model.addAttribute("companies", companyService.findAll(size, page));
        return "companies";
    }

    @GetMapping("/{companyId}")
    public String chooseCompany(Model model, @PathVariable String companyId) {
        model.addAttribute("company", companyService.findById(Long.parseLong(companyId.trim())));
        return "company";
    }

    @GetMapping("/{companyId}/employees")
    public String getEmployees(Model model, @PathVariable String companyId) {
        model.addAttribute("employees", companyService.findById(Long.parseLong(companyId.trim())));
        return "employees";
    }

    @GetMapping("/{companyId}/employees/{employeeId}")
    public String getEmployee(Model model, @PathVariable String employeeId) {
        model.addAttribute("employee", employeeService.findById(Long.parseLong(employeeId.trim())));
        return "employees";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{companyId}/employees/{employeeId}")
    public String deleteEmployee(Model model, @PathVariable String companyId, @PathVariable String employeeId) {
        Optional<Employee> employee = employeeService.findById(Long.parseLong(employeeId.trim()));
        Optional<Company> company = companyService.findById(Long.parseLong(companyId.trim()));
        if (employee.isPresent() && company.isPresent()) {
            company.get().getEmployees().removeIf(emp -> Objects.equals(emp.getId(), Long.valueOf(employeeId.trim())));
            companyService.update(company.get());
            employeeService.deleteById(Long.parseLong(employeeId.trim()));

            model.addAttribute("company", companyService.findById(Long.valueOf(companyId.trim())));
            return "company";
        }
        model.addAttribute("employee", employee);
        return "employee";
    }

}
