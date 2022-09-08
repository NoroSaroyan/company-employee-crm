package ru.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.project.entity.Company;
import ru.project.entity.Employee;
import ru.project.exception.CompanyNotFoundException;
import ru.project.service.CompanyService;
import ru.project.service.EmployeeService;

import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
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
        Employee emp;
        Optional<Company> got = companyService.findById(Long.parseLong(employeeId.trim()));
        if (got.isPresent()) {
            for (Employee employee : got.get().getEmployees()) {
                if (employee.getId().equals(Long.valueOf(employeeId.trim()))) {
                    model.addAttribute("employee", employee);
                    return "employees";
                }
            }
        }
        return "redirect:/{companyId}/employees";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{companyId}/employees/{employeeId}")
    public String deleteEmployee(Model model, @PathVariable String companyId, @PathVariable String employeeId) {
        Optional<Company> company = companyService.findById(Long.valueOf(companyId.trim()));
        Optional<Employee> employee = employeeService.findById(Long.parseLong(employeeId.trim()));
        if (employee.isPresent()) {
            company.ifPresent(company1 -> company1.getEmployees().remove(employee.get()));
            employeeService.deleteById(Long.valueOf(employeeId.trim()));
            companyService.update(company.get());
            return "redirect:/{companyId}/employees";
        }
        return "redirect:/{companyId}/employees/";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("company/add")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        companyService.save(company);
        if (company.getEmployees() != null) {
            employeeService.saveAll(company.getEmployees());
        }
        return ResponseEntity.ok(company);
    }

    @PatchMapping("/{companyId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Company> editCompany(@PathVariable String companyId, @RequestBody Company update) throws CompanyNotFoundException {
        try {
            Company got = companyService.findById(Long.parseLong(companyId.trim())).get();
            got.setEmployees(update.getEmployees());
            got.setWebsite(update.getWebsite());
            got.setName(update.getName());
            got.setEmail(update.getEmail());
            companyService.update(got);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{companyId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteCompany(@PathVariable String companyId) {
        if (companyService.existsById(Long.valueOf(companyId.trim()))) {
            companyService.deleteById(Long.valueOf(companyId.trim()));
            return "redirect:/companies";
        }
        return "redirect:/error503";
    }
}
