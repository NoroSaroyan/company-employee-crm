package ru.project.utils;

import ru.project.entity.Employee;

public class EmployeeUtils {

    //Add company before using
    public static Employee getTestEmployee(){
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setPhone_number("89001234567");
        employee.setEmail("johndoe@gmail.com");
        return employee;
    }
}
