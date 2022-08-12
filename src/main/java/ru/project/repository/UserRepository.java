package ru.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.project.entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
