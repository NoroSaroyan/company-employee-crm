package ru.project.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.project.entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

    @Override
    @NonNull
    Optional<Employee> findById(@NonNull Long id);

    Optional<Employee> getEmployeesByCompany_Id(Long companyId);

    Optional<Employee> findByCompany_Id(Long companyId);

    //TODO should add company too.
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Employee e set e.name=:name, e.surname=:surname,e.phone_number=:phone_number, e.email=:email where e.id =:employeeId")
    void update(@Param("employeeId") Long id, @Param("name") String name,
                @Param("surname") String surname, @Param("phone_number") String phone_number,
                @Param("email") String email);

}
