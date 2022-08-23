package ru.project.utils;

import ru.project.entity.Company;
import ru.project.entity.Employee;

import java.util.List;

public class CompanyUtils {
    public static Company getTestCompany() {
        Company company = new Company();
        company.setEmail("company@test.com");
        company.setName("testCompany");
        company.setWebsite("testCompany.com");
        return company;
    }
}
