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
import ru.project.utils.PagingUtil;

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

        List<Integer> numberOfPages = PagingUtil.findNumberOfPages(companyService.getCount());

        model.addAttribute("companies", companyList);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("currentPage", page);

        return "companies";
    }

    @GetMapping("companies/{companyId}")
    public String getCompany(Model model, @PathVariable Long companyId) {
        model.addAttribute("company", companyService.findById(companyId));
        return "company";
    }

    @GetMapping("company/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addCompany(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "add_company";
    }

    @PostMapping("company/post")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addCompany(@ModelAttribute("company") Company company) {
        companyService.save(company);
        return "redirect:/companies/" + company.getId().toString();
    }



    @GetMapping("companies/{companyId}/edit")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editCompany(Model model, @PathVariable Long companyId) {
        Company company = companyService.findById(companyId).orElseThrow(IllegalArgumentException::new);

        model.addAttribute("company", company);

        return "edit_company";
    }

    @PostMapping(value = "companies/{companyId}/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editCompany(@PathVariable Long companyId, @ModelAttribute("company") Company update) {
        update.setId(companyId);
        try {
            companyService.update(update);
            return "redirect:/companies/" + companyId.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "error/404";
        }
    }


    @GetMapping("companies/{companyId}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteCompany(@PathVariable Long companyId) {
        Optional<Company> company = companyService.findById(companyId);
        boolean isEmpty = employeeService.findAllByCompanyId(companyId, 0, 1).isEmpty();
        if (company.isPresent() && isEmpty) {
            companyService.deleteById(companyId);
            return "redirect:/companies";
        }
        return "error/503";
    }

    @GetMapping("companies/{companyId}/employees")
    public String getEmployees(Model model, @PathVariable Long companyId,
                               @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                               @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<Employee> employees = employeeService.findAllByCompanyId(companyId, page, size);

        List<Integer> numberOfPages = PagingUtil.findNumberOfPages(companyService.findAll().size());

        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("employees", employees);
        model.addAttribute("currentPage", page);
        return "employees";
    }

    @GetMapping("companies/{companyId}/employees/{employeeId}")
    public String getEmployee(Model model, @PathVariable Long employeeId, @PathVariable Long companyId) {
        model.addAttribute("employee", employeeService.findById(employeeId));
        return "employee";
    }

    @GetMapping("companies/{companyId}/employees/{employeeId}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteEmployee(@PathVariable Long companyId, @PathVariable Long employeeId) {
        employeeService.deleteById(employeeId);
        return "redirect:/companies/{companyId}/employees";
    }

    @GetMapping("companies/{companyId}/employee/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addEmployee(Model model, @PathVariable Long companyId) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("companyId", companyId);
        return "add_employee";
    }

    @PostMapping("companies/{companyId}/employee/post")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addEmployee(@ModelAttribute("employee") Employee employee,
                              @ModelAttribute("company") Company company, @PathVariable Long companyId) throws CompanyNotFoundException {

        employee.setCompany(companyService.findById(companyId).orElseThrow(
                () -> new CompanyNotFoundException("Company with id:" + companyId.toString() + " not found")));
        employeeService.save(employee);
        return "redirect:/companies/" + companyId.toString() + "/employees/";
    }

    @GetMapping(value = "companies/{companyId}/employees/{employeeId}/edit")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editEmployee(Model model, @PathVariable Long employeeId, @PathVariable String companyId) {
        Employee employee = employeeService.findById(employeeId).orElseThrow(IllegalArgumentException::new);

        model.addAttribute("employee", employee);

        return "edit_employee";
    }

    @PostMapping(value = "companies/{companyId}/employees/{employeeId}/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editEmployee(@PathVariable Long employeeId, @ModelAttribute Employee update, @PathVariable Long companyId) {
        update.setId(employeeId);
        try {
            employeeService.update(update);
            return "redirect:/companies/" + companyId + "/employees";
        } catch (Exception e) {
            return "error/503";
        }
    }
}
