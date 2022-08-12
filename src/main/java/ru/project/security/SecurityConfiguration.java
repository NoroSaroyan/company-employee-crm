package ru.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**");
    }
    protected void configure(HttpSecurity http) throws Exception {

//        "/companies/{companyId}/delete", "/companies/{companyId}/add",
//                        "/companies/{companyId}/edit",
//                        "/companies/{companyId}/employees/employeeId/delete", "/companies/{companyId}/employees/employeeId/edit",
//                        "employees/employee/add"

        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/home", "/index").permitAll()
                .antMatchers("/admin").authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html").loginProcessingUrl("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login")
                .usernameParameter("login").passwordParameter("entry")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies()
                .permitAll();
    }
}

