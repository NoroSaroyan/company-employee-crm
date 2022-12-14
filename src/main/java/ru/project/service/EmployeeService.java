package ru.project.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<Employee> findAll(int page, int size, Long companyId) {

        if (size > 10) {
            size = 10;
        }
        return employeeRepository.findAll(PageRequest.of(page, size)).stream().toList();

    }

    public List<Employee> findAllByCompanyId(Long companyId, int page, int size) {
        if (size > 10) {
            size = 10;
        }
        return employeeRepository.findAllByCompanyId(companyId, PageRequest.of(page, size)).stream().toList();
    }

    public void delete(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }


    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public void saveAll(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional
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

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}
