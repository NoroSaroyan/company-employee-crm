package ru.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomepageController {
    @GetMapping(value = {"/", "/index", "/home"})
    public String homePage(Model model , Principal principal){
        return "home";
    }
    @RequestMapping("/login")
    public String authorise(){
        return "login";
    }


}
