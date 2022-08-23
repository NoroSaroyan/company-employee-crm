package ru.project.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.project.entity.Authority;
import ru.project.entity.User;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.*;

@DisplayName("verify UserDetailsService")
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    @Mock private UserService userService;

    @InjectMocks private UserDetailsServiceImpl userDetailsService;

    @DisplayName("can load existing user")
    @Test
    void loadUserByUsername() {
        given(userService.findByEmail(any()))
                .willReturn(
                        Optional.of(
                                new User(
                                        "test@gmail.com", "password", List.of(new Authority("ADMIN")))));

        UserDetails userDetails = userDetailsService.loadUserByUsername("test@gmail.com");
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_ADMIN");

    }

    @DisplayName("reports expected error when user does not exist")
    @Test
    void loadUserByUsernameNotFound() {
        given(userService.findByEmail(any())).willReturn(Optional.empty());
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userDetailsService.loadUserByUsername("test@gmail.com"))
                .withMessage("No user found for test@gmail.com")
                .withNoCause();
    }

}