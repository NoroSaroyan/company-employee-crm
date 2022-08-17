package ru.project.repository;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.project.entity.User;
import java.util.Optional;
import java.util.OptionalInt;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    @NonNull
    Optional<User> findById(@NonNull Long id);

    Optional<User> findByEmail(String email);
    @Override
    void delete(@NonNull User entity);

    Optional<User> getById(Long id);
}
