package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.SignUpRequest;
import com.example.schoolmanagementsystem.entity.PasswordResetToken;
import com.example.schoolmanagementsystem.entity.Role;
import com.example.schoolmanagementsystem.entity.User;
import com.example.schoolmanagementsystem.exceptions.UserNotFoundException;
import com.example.schoolmanagementsystem.repository.PasswordResetTokenRepository;
import com.example.schoolmanagementsystem.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
     UserRepository userRepository;
    @Autowired
     PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User signup(SignUpRequest signUpRequest ){

        // Check if the password meets the requirements
        String password = signUpRequest.getPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 8 characters long, contain at least one uppercase letter, and one special character.");
        }


        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and confirmed password do not match");
        }

        User user= new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail((signUpRequest.getEmail()));
        user.setUsername(signUpRequest.getUsername());
        user.setRole(Role.valueOf(signUpRequest.getRole()));
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return  userRepository.save(user);
    }

    // Method to validate password constraints
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Password must contain at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Password must contain at least one special character
        if (!password.matches(".*[!@#$%^&*()-+=].*")) {
            return false;
        }

        return true;
    }
    @Override
    public User signin(String usernameOrEmail, String password) {
        // Try to find the user by email
        User user = userRepository.findByEmail(usernameOrEmail);

        // If user not found by email, try to find by username
        if (user == null) {
            user = userRepository.findByUsername(usernameOrEmail);
        }

        // Check if the user exists and the password is correct
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null; // Invalid credentials
    }

    @Override
    public String generatePasswordResetToken(User user) {
        // Generate a random token for password reset
        String token = UUID.randomUUID().toString();

        // Set expiry date (example: 24 hours from now)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plusHours(24); // Adjust expiry duration as needed

        // Save the token in the database along with the user
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(expiryDate); // Set expiry date here

        passwordResetTokenRepository.save(passwordResetToken);

        return token;
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(User updatedUser) {
        Long id = updatedUser.getId();
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setLastName(updatedUser.getLastName());

            // Update other fields as needed
            return userRepository.save(existingUser);
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public void deleteUserById(Long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }


    public UserDetailsService userDetailsService(){
        return  new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) {
                return userRepository.findByEmail(email);
                        //.orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
