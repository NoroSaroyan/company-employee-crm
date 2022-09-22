package ru.project.service;

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
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("---------------");
        System.out.println(email);
        System.out.println("---------------");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");

        Optional<User> mayBeUser = userRepository.findByEmail(email);

        if (mayBeUser.isEmpty()) {
            throw new UsernameNotFoundException("No user found for " + email);
        }

        User user = mayBeUser.get();

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority.getName().toUpperCase())).collect(Collectors.toList()))
                .build();
    }

}
