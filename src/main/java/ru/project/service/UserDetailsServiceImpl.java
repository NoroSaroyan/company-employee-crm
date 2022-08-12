package ru.project.service;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.project.entity.User;
import ru.project.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("BEFORE mayBeUSER ___________________");
        Optional<User> mayBeUser = userRepository.findByEmail(email);
        System.out.println("AFTER  mayBeUSER ___________________");

        if (mayBeUser.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        User user = mayBeUser.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase())).collect(Collectors.toList()))
                .build();    }

    public boolean checkByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
