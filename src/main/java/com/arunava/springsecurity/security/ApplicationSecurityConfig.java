package com.arunava.springsecurity.security;

import com.arunava.springsecurity.model.ApplicationUserPermission;
import com.arunava.springsecurity.model.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.arunava.springsecurity.model.ApplicationUserPermission.*;
import static com.arunava.springsecurity.model.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

    @Autowired
    PasswordEncoder passwordEncoder;


    @Bean
    UserDetailsService userDetailsService(){
        UserDetails arunava = User.builder()
                .username("arunava")
                .password(passwordEncoder.encode("arunava"))
                .authorities(STUDENT.getGrantedAuthority())
//                .roles(STUDENT.name()) //ROLE_STUDENT
                .build();

        UserDetails lindaUser = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("arunava123"))
                .authorities(ADMIN.getGrantedAuthority())
//                .roles(ADMIN.name()) //ROLE_ADMIN
                .build();
        UserDetails tom = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("arunava123"))
                .authorities(ADMINTRAINEE.getGrantedAuthority())
//                .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
                .build();
        return new InMemoryUserDetailsManager(arunava ,lindaUser,tom);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(  "/", "index", "/css/*", "/js/*").permitAll()
                .requestMatchers("/api/**").hasRole(STUDENT.name())
                .requestMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .requestMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .requestMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .requestMatchers("/management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();

    }
}
