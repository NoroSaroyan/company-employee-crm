package ru.project.repository;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.project.entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {

    @Override
    Optional<Employee> findById(@NonNull Long id);

    Optional<Employee> getAllByCompany_Id(Long companyId);

}
