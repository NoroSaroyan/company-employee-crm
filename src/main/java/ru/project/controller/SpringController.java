package ru.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("company/add")
    public String addCompany(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "add_company";
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("company/post")
    public String addCompany(@ModelAttribute("company") Company company) {
        companyService.save(company);
        return "redirect:/companies/" + company.getId().toString();
    }

    @GetMapping("companies/{companyId}/edit")
    public String editCompany(Model model, @PathVariable Long companyId) {
        Company company = companyService.findById(companyId).orElseThrow(IllegalArgumentException::new);

        model.addAttribute("company", company);

        return "edit_company";
    }

    @PostMapping(value = "companies/{companyId}/update")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editCompany(@PathVariable Long companyId, @ModelAttribute("company") Company update) {

        try {
            companyService.update(update);
            return "redirect:/companies/" + companyId.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "error/404";
        }
    }

    @GetMapping("companies/{companyId}/delete")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteCompany(@PathVariable Long companyId) {
        Optional<Company> company = companyService.findById(companyId);
        boolean isEmpty = employeeService.findAllByCompanyId(companyId, 0, 1).isEmpty();
        if (company.isPresent() && isEmpty) {
            companyService.deleteById(companyId);
            return "redirect:/companies";
        }
        return "error/503";
    }

    @GetMapping("companies/{companyId}/employee/add")
    public String addEmployee(Model model, @PathVariable Long companyId) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.println(companyId.toString());
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        model.addAttribute("employee", new Employee());
        model.addAttribute("companyId", companyId);
        return "add_employee";
    }

    @PostMapping("companies/{companyId}/employee/post")
//  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addEmployee(@ModelAttribute("employee") Employee employee,
                              @ModelAttribute("company") Company company, @PathVariable Long companyId) throws CompanyNotFoundException {

        employee.setCompany(companyService.findById(companyId).orElseThrow(
                () -> new CompanyNotFoundException("Company with id:" + companyId.toString() + " not found")));
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("employee = " + employee);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        employeeService.save(employee);
        return "redirect:/companies/" + companyId.toString() +"/employees/";
    }

    @PatchMapping("companies/{companyId}/employees/{employeeId}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editEmployee(@PathVariable Long employeeId, @RequestBody Employee update, @PathVariable Long companyId) {
        try {
            employeeService.update(update);
            return "redirect:companies/" + companyId.toString() + "/employees/";

        } catch (Exception e) {
            return "error/503";
        }
    }
}
