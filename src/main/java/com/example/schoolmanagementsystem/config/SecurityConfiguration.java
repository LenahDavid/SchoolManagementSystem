package com.example.schoolmanagementsystem.config;

import com.example.schoolmanagementsystem.entity.Role;
import com.example.schoolmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.schoolmanagementsystem.entity.Permission.*;
import static com.example.schoolmanagementsystem.entity.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

@Autowired
UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/swagger-ui.html","/swagger-ui/**","/v3/api-docs/**","/api/v1/auth/**","api/v1/user/**")

                        .permitAll()
                        .requestMatchers("api/v1/headteacher/**").hasAnyRole(ADMIN.name(),HEADTEACHER.name())
                        .requestMatchers(GET,"api/v1/headteacher/**").hasAnyAuthority(ADMIN_READ.name(),HEADTEACHER_READ.name())
                        .requestMatchers(POST,"api/v1/headteacher/**").hasAnyAuthority(ADMIN_CREATE.name(),HEADTEACHER_CREATE.name())
                        .requestMatchers(PUT,"api/v1/headteacher/**").hasAnyAuthority(ADMIN_UPDATE.name(),HEADTEACHER_UPDATE.name())
                        .requestMatchers(DELETE,"api/v1/headteacher/**").hasAnyAuthority(ADMIN_DELETE.name(),HEADTEACHER_DELETE.name())

                        .requestMatchers("api/v1/teacher/**").hasAnyRole(ADMIN.name(),HEADTEACHER.name(),TEACHER.name())
                        .requestMatchers(GET,"api/v1/teacher/**").hasAnyAuthority(ADMIN_READ.name(),HEADTEACHER_READ.name(),TEACHER_READ.name())
                        .requestMatchers(POST,"api/v1/teacher/**").hasAnyAuthority(ADMIN_CREATE.name(),HEADTEACHER_CREATE.name(),TEACHER_CREATE.name())
                        .requestMatchers(PUT,"api/v1/teacher/**").hasAnyAuthority(ADMIN_UPDATE.name(),HEADTEACHER_UPDATE.name(),TEACHER_UPDATE.name())
                        .requestMatchers(DELETE,"api/v1/teacher/**").hasAnyAuthority(ADMIN_DELETE.name(),HEADTEACHER_DELETE.name(),TEACHER_DELETE.name())

                        .requestMatchers("api/v1/user/**").hasAnyRole(ADMIN.name(),HEADTEACHER.name(),TEACHER.name(),USER.name())
                        .requestMatchers(GET,"api/v1/user/**").hasAnyAuthority(ADMIN_READ.name(),HEADTEACHER_READ.name(),TEACHER_READ.name(),USER_READ.name())
                        .requestMatchers(POST,"api/v1/user/**").hasAnyAuthority(ADMIN_CREATE.name(),HEADTEACHER_CREATE.name(),TEACHER_CREATE.name(),USER_CREATE.name())
                        .requestMatchers(PUT,"api/v1/user/**").hasAnyAuthority(ADMIN_UPDATE.name(),HEADTEACHER_UPDATE.name(),TEACHER_UPDATE.name(),USER_UPDATE.name())
                        .requestMatchers(DELETE,"api/v1/user/**").hasAnyAuthority(ADMIN_DELETE.name(),HEADTEACHER_DELETE.name(),TEACHER_DELETE.name(),USER_DELETE.name())

                        .requestMatchers("api/v1/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(GET,"api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST,"api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT,"api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE,"api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())

                        .anyRequest().authenticated())
                .sessionManagement(manager ->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws  Exception{
        return config.getAuthenticationManager();
    }
}
