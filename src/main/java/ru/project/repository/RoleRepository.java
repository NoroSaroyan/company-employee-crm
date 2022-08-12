package ru.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.project.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

    Optional<Role> findByName(String name);

    @Override
    Iterable<Role> findAll();
}