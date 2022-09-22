package ru.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.project.entity.Company;
import ru.project.entity.Employee;
import ru.project.exception.CompanyNotFoundException;
import ru.project.service.CompanyService;
import ru.project.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class SpringController {
    private final CompanyService companyService;

    private final EmployeeService employeeService;

    @Autowired
    public SpringController(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping("companies")
    public String getAllCompanies(Model model,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<Company> companyList = companyService.findAll(page, size);
        model.addAttribute("companies", companyList);
        return "home";
    }

    @GetMapping("companies/{companyId}")
    public String getCompany(Model model, @PathVariable Long companyId) {
        model.addAttribute("company", companyService.findById(companyId));
        return "company";
    }

    @GetMapping("companies/{companyId}/employees")
    public String getEmployees(Model model, @PathVariable Long companyId) {
        List<Employee> employees = employeeService.findAllByCompanyId(companyId, 0, 10);
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("companies/{companyId}/employees/{employeeId}")
    public String getEmployee(Model model, @PathVariable Long employeeId, @PathVariable Long companyId) {
        Optional<Employee> employee = employeeService.findById(employeeId);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employee";
        }
        return "error/404";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("companies/{companyId}/employees/{employeeId}")
    public String deleteEmployee(Model model, @PathVariable Long companyId, @PathVariable Long employeeId) {
        employeeService.deleteById(employeeId);
        return "redirect:/{companyId}/employees";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("companies/company")
    public String addCompany(@RequestBody Company company) {
        companyService.save(company);
        return "redirect:/companies/" + company.getId().toString();
    }

    @PatchMapping("companies/{companyId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editCompany(@PathVariable Long companyId, @RequestBody Company update) throws CompanyNotFoundException {
        try {
            companyService.update(update);
            return "redirect:/companies/" + companyId.toString();
        } catch (Exception e) {
            return "error/404";
        }
    }

    @DeleteMapping("companies/{companyId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteCompany(@PathVariable String companyId) {
        Optional<Company> company = companyService.findById(Long.valueOf(companyId.trim()));
        boolean isEmpty = employeeService.findAllByCompanyId(Long.valueOf(companyId.trim()), 0, 1).isEmpty();
        if (company.isPresent() && isEmpty) {
            companyService.deleteById(Long.valueOf(companyId.trim()));
            return "redirect:/all";
        }
        return "error/503";
    }

    @PostMapping("employee/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return ResponseEntity.ok(employee);
    }

    @PatchMapping("companies/{companyId}/employees/{employeeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editEmployee(@PathVariable Long employeeId, @RequestBody Employee update, @PathVariable Long companyId) {
        try {
            employeeService.update(update);
            return "redirect:companies/" + companyId.toString() + "/employees/";

        } catch (Exception e) {
            return "error/503";
        }
    }
}
