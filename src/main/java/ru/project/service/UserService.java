package ru.project.service;

import org.springframework.stereotype.Service;
import ru.project.entity.User;
import ru.project.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service("userService")
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void delete(User entity) {
        if (entity != null) {
            repository.delete(entity);
        } else {
            throw new RuntimeException("Null entity");
        }
    }

    //IDK?
    public boolean update(User entity) {
//        Optional<User> mayBeUser = repository.getById(entity.getId());
//        if (mayBeUser.isEmpty()){
//            throw new RuntimeException();
//        }else{
//            mayBeUser.get().setEmail(entity.getEmail());
//            mayBeUser.get().setPassword(entity.getPassword());
//            repository.save(mayBeUser.get());
//        }
        if (repository.getById(entity.getId()).isPresent()) {
            repository.getById(entity.getId()).get().setEmail(entity.getEmail());
            repository.getById(entity.getId()).get().setPassword(entity.getPassword());
            return true;
        }
        return false;
    }

    public void add(User user) {
        if (user != null) {
            repository.save(user);
        }
    }

    public Optional<User> get(Long id){
        return repository.findById(id);
    }
}
