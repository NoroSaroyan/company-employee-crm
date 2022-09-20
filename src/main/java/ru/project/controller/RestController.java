package ru.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.project.entity.Company;
import ru.project.entity.Employee;
import ru.project.exception.CompanyNotFoundException;
import ru.project.service.CompanyService;
import ru.project.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class RestController {
    private final CompanyService companyService;

    private final EmployeeService employeeService;

    @Autowired
    public RestController(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping("/companies/{companyId}")
    public @ResponseBody ResponseEntity<Company> getCompany(@PathVariable Long companyId) {
        Optional<Company> company = companyService.findById(companyId);
        return ResponseEntity.of(company);
    }

    @GetMapping("/companies")
    public @ResponseBody ResponseEntity<List<Company>> getAllCompanies(
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        List<Company> companyList = companyService.findAll(pageNumber, size);
        return ResponseEntity.ok(companyList);
    }

    @GetMapping("/companies/{companyId}/employees")
    public @ResponseBody ResponseEntity<List<Employee>> getEmployees(
            @PathVariable Long companyId, @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<Employee> employees = employeeService.findAllByCompanyId(companyId, pageNumber, size);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/companies/{companyId}/employees/{employeeId}")
    public @ResponseBody ResponseEntity<Employee> getEmployee(@PathVariable Long companyId, @PathVariable Long employeeId) {
        Optional<Employee> employee = employeeService.findById(employeeId);
        return ResponseEntity.of(employee);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/companies/{companyId}/employees/{employeeId}")
    public @ResponseBody ResponseEntity<Long> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteById(employeeId);
        if (employeeService.existsById(employeeId)) {
            return ResponseEntity.ok(employeeId);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/companies/company")
    public @ResponseBody ResponseEntity<Company> addCompany(@RequestBody Company company) {
        companyService.save(company);
        return ResponseEntity.ok(company);
    }

    @PatchMapping("/companies/{companyId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public @ResponseBody ResponseEntity<Company> editCompany(@PathVariable Long companyId, @RequestBody Company update) throws CompanyNotFoundException {
        try {
            companyService.update(update);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/companies/{companyId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public @ResponseBody ResponseEntity<Long> deleteCompany(@PathVariable Long companyId) {
        companyService.deleteById(companyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/employees")
    public @ResponseBody ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(employeeService.findAll(0, 10));
    }

    @GetMapping("/employees/{employeeId}")
    public @ResponseBody ResponseEntity<Employee> getEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.of(employeeService.findById(employeeId));
    }

    @PostMapping("/employees/employee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public @ResponseBody ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public @ResponseBody ResponseEntity<Long> deleteEmployeeFromEmployees(@PathVariable Long employeeId) {
        employeeService.deleteById(employeeId);
        if (!employeeService.existsById(employeeId)) {
            return ResponseEntity.ok(employeeId);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping("/employees/{employeeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Employee> editEmployee(@PathVariable String employeeId, @RequestBody Employee update) {
        try {
            employeeService.update(update);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
