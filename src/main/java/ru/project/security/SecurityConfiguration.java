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
    public void configure(WebSecurity web){
        web.ignoring().antMatchers( "/js/**","/css/**","/webjars/**");

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/home", "/index").permitAll()
                .antMatchers( "/js/**","/css/**","/webjars/**").permitAll()
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

