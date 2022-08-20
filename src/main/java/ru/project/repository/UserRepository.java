package ru.project.repository;

import lombok.NonNull;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.project.entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Override
    @NonNull
    Optional<User> findById(@NonNull Long id);

    Optional<User> findByEmail(String email);
}
