package com.example.schoolmanagementsystem.service;


import com.example.schoolmanagementsystem.dto.SignUpRequest;
import com.example.schoolmanagementsystem.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User signup(SignUpRequest signUpRequest);
    User signin(String usernameOrEmail, String password);
    String generatePasswordResetToken(User user);
    User findByEmail(String email);
    User save(User user);
    List<User> getAllUsers();
    UserDetailsService userDetailsService();

    User getUserById(Long id);

    User updateUser(User user);

    void deleteUserById(Long id);

}
