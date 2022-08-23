package ru.project.utils;

import ru.project.entity.Company;
import ru.project.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class CompanyUtils {
    public static Company getTestCompany() {
        Company company = new Company();
        company.setEmail("company@test.com");
        company.setName("testCompany");
        company.setWebsite("testCompany.com");
        return company;
    }

    public static List<Company> getTestCompanyList(){
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1L, "google", "google.com", "google@gmail.com"));
        companyList.add(new Company(2L, "yandex", "yandex.ru", "company@yandex.ru"));
        companyList.add(new Company(3L, "VK", "vk.com", "vk@gmail.com"));

        return companyList;
    }
}
