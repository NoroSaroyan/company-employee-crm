package ru.project.utils;

import ru.project.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeUtils {

    /*
    * Set company before using the employee
    * NOTE: don't try setting company here because it will cause StackOverflowException
    * */
    public static Employee getTestEmployee() {

        Employee employee = new Employee();
        employee.setEmail("JohnDoe@gmail.com");
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setPhone_number("+79001234567");

        return employee;
    }

    /*
    * Employees' companies' are CompanyUtils' companies respective(0==0, 1==1, ..., (indexes in lists))
    * NOTE THAT IF USING getTestCompanies in tests,
    * you should set the companies' employees(it should be the employees below in their respective places(indexes) in their lists)
    * */
    public static List<Employee> getTestEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Igor", "Ilkin", "+79005700999", "IgorIlkin@yahoo.com"));
        employeeList.add(new Employee("Nikolay", "Parshin", "+79005700239", "NikolayParshin@gmail.com"));
        employeeList.add(new Employee("Darya", "Malkova", "+79005700777", "DaryaMalkova@mail.com"));
        return employeeList;
    }
}
