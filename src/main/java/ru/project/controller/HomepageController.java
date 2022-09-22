package ru.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.project.entity.Company;
import ru.project.service.CompanyService;

import java.security.Principal;
import java.util.List;

@Controller
public class HomepageController {
    private final CompanyService companyService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public HomepageController(CompanyService companyService, PasswordEncoder passwordEncoder) {
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping(value = {"/", "/index", "/home"})
    public String homePage(Model model, Principal principal) {
        List<Company> companyList = companyService.findAll(0, 10);
        model.addAttribute("companies", companyList);
//        model.addAttribute("company", companyService.findById(1L));
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        System.out.println("--------");
        System.out.println(passwordEncoder.encode("1234test"));
        System.out.println("--------");
//        $2a$10$IHAND/u./SYfxaMGpBWTUu.SgwElfa/7fYhsPO2XErzqxZFzjTzLK
        return "login";
    }


}
