package ru.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults());
//        return http.build();
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http .csrf().disable()
                .authorizeRequests().antMatchers("/","/public").permitAll()
                .antMatchers("/authenticated", "/admin", "/companies/{companyId}/delete","/companies/{companyId}/add",
                        "/companies/{companyId}/edit",
                        "/companies/{companyId}/employees/employeeId/delete","/companies/{companyId}/employees/employeeId/edit",
                        "employees/employee/add").authenticated().and()
                .formLogin()
                .loginPage("/authorise").usernameParameter("login").passwordParameter("entry")
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
