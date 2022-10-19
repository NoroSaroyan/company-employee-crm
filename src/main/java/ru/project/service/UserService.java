package ru.project.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.project.entity.User;
import ru.project.repository.AuthorityRepository;
import ru.project.repository.UserRepository;

import java.util.Optional;

@Service("userService")
public class UserService {
    private final UserRepository repository;
    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository repository, AuthorityRepository authorityRepository) {
        this.repository = repository;
        this.authorityRepository = authorityRepository;
    }

    public Optional<User> get(Long id){
        return repository.findById(id);
    }

    public void save(User user){
        authorityRepository.saveAll(user.getAuthorities());
        repository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return Optional.ofNullable(repository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException("No User found for" + email)));
    }
}
