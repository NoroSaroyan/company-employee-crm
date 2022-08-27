package ru.project.repository;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.project.entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Override
    @NonNull
    Optional<Employee> findById(@NonNull Long id);

    Optional<Employee> getEmployeesByCompany_Id(Long companyId);


    Optional<Employee> findByCompany_Id(Long companyId);

}
