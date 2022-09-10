package ru.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.project.entity.Employee;
import ru.project.service.CompanyService;
import ru.project.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final CompanyService companyService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @GetMapping()
    public String getEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll(0, 10));
        return "employees";
    }

    @GetMapping("/{employeeId}")
    public String getEmployee(Model model, @PathVariable String employeeId) {
        model.addAttribute("employee", employeeService.findById(Long.parseLong(employeeId.trim())));
        return "employees";
    }

    @PostMapping("/employee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteById(Long.parseLong(employeeId.trim()));
        return "redirect:/employees";
    }

    @PatchMapping("/{employeeId}")
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
