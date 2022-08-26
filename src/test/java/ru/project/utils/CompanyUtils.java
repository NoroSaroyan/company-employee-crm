package ru.project.utils;

import ru.project.entity.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyUtils {
    private static Long aLong = 1L;
    public static Company getTestCompany() {
        return new Company("testCompany","testCompany.com","company@test.com");
    }

    public static List<Company> getTestCompanyList(){
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company( "google", "google.com", "google@gmail.com"));
        companyList.add(new Company( "yandex", "yandex.ru", "company@yandex.ru"));
        companyList.add(new Company( "VK", "vk.com", "vk@gmail.com"));

        return companyList;
    }

}
