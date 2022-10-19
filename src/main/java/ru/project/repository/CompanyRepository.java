package ru.project.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.project.entity.Company;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {

    @Override
    Optional<Company> findById(@NonNull Long id);

    Optional<Company> findByName(String name);

    @Override
    boolean existsById(@NonNull Long id);

    @Override
    void deleteById(@NonNull Long id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Company c set c.name=:name, c.website=:website, c.email=:email where c.id =:companyId")
    void update(@Param("companyId") Long id, @Param("name") String name, @Param("website") String website, @Param("email") String email);


    List<Company> findAll();
}
