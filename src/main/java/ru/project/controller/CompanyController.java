package ru.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.project.entity.Company;
import ru.project.service.CompanyService;

import java.util.Optional;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

//    @GetMapping("")
//    public String all(Model model) {
//        model.addAttribute("companies", companyService.getAll());
//        return "companies";
//    }

    @GetMapping("/")
    public String getAll(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                         @RequestParam(value = "size", required = false, defaultValue = "3") int size, Model model) {
        model.addAttribute("companies", companyService.getAll());
        return "companies";
    }

    @GetMapping("/company/{id}")
    public String getById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("company{id}", companyService.getById(id));
        return "company";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("edit/company/{id}")
    public String edit(@PathVariable("id") Long companyId, Model model) {
        Optional<Company> company = companyService.getById(companyId);
        if (company.isPresent()) {
            model.addAttribute("companyForm", company);
            model.addAttribute("method", "edit");
            return "company";
        } else {
            //
            return HttpStatus.NOT_FOUND.name();
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/company/edit/{id}")
    public String edit(@PathVariable("id") Long companyId, @ModelAttribute("companyForm") Company company,
                       BindingResult bindingResult, Model model) {
        //TODO need to get errors from companyForm
        if (bindingResult.hasErrors()) {
            LOGGER.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "edit");
            return "company";
        }
        companyService.edit(companyId, company);
        LOGGER.debug(String.format("Company with id: %s has been successfully edited.", companyId));

        return "redirect:/home";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long companyId) {
        companyService.delete(companyId);
        return "redirect:/companies/";
    }


}
