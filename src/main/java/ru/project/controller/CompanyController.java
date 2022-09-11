//package ru.project.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.project.entity.Company;
//import ru.project.entity.Employee;
//import ru.project.exception.CompanyNotFoundException;
//import ru.project.service.CompanyService;
//import ru.project.service.EmployeeService;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/companies")
//public class CompanyController {
//
//    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
//    private final CompanyService companyService;
//
//    private final EmployeeService employeeService;
//
//    @Autowired
//    public CompanyController(CompanyService companyService, EmployeeService employeeService) {
//        this.companyService = companyService;
//        this.employeeService = employeeService;
//    }
//
//    @GetMapping("/")
//    public String getAllCompanies(Model model) {
//        List<Company> companyList = companyService.findAll(0, 10);
//        model.addAttribute("companies", companyList);
//        return "home";
//    }
//
//
//    //check for existence and long value
//    @GetMapping("/{companyId}")
//    public String getCompany(Model model, @PathVariable String companyId) {
//        model.addAttribute("company", companyService.findById(Long.parseLong(companyId.trim())));
//        return "company";
//    }
//
//
//    @GetMapping("/{companyId}/employees")
//    public String getEmployees(Model model, @PathVariable String companyId) {
//        List<Employee> employees = employeeService.findAllByCompanyId(Long.parseLong(companyId.trim()), 0, 10);
//        model.addAttribute("employees", employees);
//        return "employees";
//    }
//
//    @GetMapping("/{companyId}/employees/{employeeId}")
//    public String getEmployee(Model model, @PathVariable String employeeId) {
//        Optional<Employee> employee = employeeService.findById(Long.valueOf(employeeId.trim()));
//        if (employee.isPresent()) {
//            model.addAttribute("employee", employee.get());
//            return "employees";
//        }
//        return "redirect:/404";
//    }
//
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @DeleteMapping("/{companyId}/employees/{employeeId}")
//    public String deleteEmployee(Model model, @PathVariable String companyId, @PathVariable String employeeId) {
//        employeeService.deleteById(Long.valueOf(employeeId.trim()));
//        return "redirect:/{companyId}/employees";
//    }
//
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @PostMapping("/company")
//    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
////        company.setEmployees(Collections.emptyList());
//        companyService.save(company);
//        return ResponseEntity.ok(company);
//    }
//
//    @PatchMapping("/{companyId}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public ResponseEntity<Company> editCompany(@PathVariable String companyId, @RequestBody Company update) throws CompanyNotFoundException {
//        try {
//            companyService.update(update);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/{companyId}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String deleteCompany(@PathVariable String companyId) {
//        Optional<Company> company = companyService.findById(Long.valueOf(companyId.trim()));
//        boolean isEmpty = employeeService.findAllByCompanyId(Long.valueOf(companyId.trim()), 0, 1).isEmpty();
//        if (company.isPresent() && isEmpty) {
//            companyService.deleteById(Long.valueOf(companyId.trim()));
//            return "redirect:/all";
//        }
//        return "redirect:/error503";
//    }
//}
