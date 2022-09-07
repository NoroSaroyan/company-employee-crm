package ru.project.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        //  "/css/**"
        web.ignoring().antMatchers( "/js/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/home", "/index").permitAll()
                .antMatchers("/admin").authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html").loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin", true)
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

