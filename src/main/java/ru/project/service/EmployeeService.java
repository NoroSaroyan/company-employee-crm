package ru.project.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.project.entity.Employee;
import ru.project.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll(int page, int size) {

        if (size > 10) {
            size = 10;
        }
        return employeeRepository.findAll(PageRequest.of(page, size)).stream().toList();

    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public void saveAll(List<Employee> employees){
        employeeRepository.saveAll(employees);
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public void update(Employee employee) {
        employeeRepository.update(
                employee.getId(),
                employee.getName(),
                employee.getSurname(),
                employee.getPhone_number(),
                employee.getEmail());
    }

    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    public void deleteById(Long id){
        employeeRepository.deleteById(id);
    }
}
