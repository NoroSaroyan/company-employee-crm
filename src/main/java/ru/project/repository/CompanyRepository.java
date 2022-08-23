package ru.project.repository;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.project.entity.Company;
import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company,Long> {

    @Override
    Optional<Company> findById(@NonNull Long id);

    Optional<Company> findByName(String name);

    @Override
    boolean existsById(@NonNull Long id);

    @Override
    void deleteById(@NonNull Long id);

}
