package ru.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.project.entity.Company;
import ru.project.service.CompanyService;
import ru.project.service.EmployeeService;

import java.security.Principal;
import java.util.List;

@Controller
public class HomepageController {

    private final EmployeeService employeeService;

    private final CompanyService companyService;

    @Autowired
    public HomepageController(EmployeeService employeeService, CompanyService companyService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping(value = {"/", "/index", "/home"})
    public String homePage(Model model, Principal principal) {
        List<Company> companyList = getAllCompanies(0,10);
        model.addAttribute("companies", companyList);
//        model.addAttribute("company", companyService.findById(1L));
        return "home";
    }

    @RequestMapping("/login")
    public String authorise() {
        return "login";
    }

    private List<Company> getAllCompanies(int page, int size) {
        return companyService.findAll(page, size);
    }

}
