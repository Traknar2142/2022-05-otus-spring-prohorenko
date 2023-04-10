package ru.otus.homework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.otus.homework.enums.Authority;

@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/", "/list").hasAnyAuthority(Authority.READ.name())
                        .antMatchers("/add").hasAnyAuthority(Authority.CREATE.name())
                        .antMatchers("/edit").hasAnyAuthority(Authority.UPDATE.name())
                        .anyRequest().hasAnyAuthority(Authority.DELETE.name())
                ).formLogin();
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
