package com.example.schoolmanagementsystem.controllers;

import com.example.schoolmanagementsystem.dto.ForgotPasswordRequest;
import com.example.schoolmanagementsystem.dto.SignInRequest;
import com.example.schoolmanagementsystem.dto.SignUpRequest;
import com.example.schoolmanagementsystem.entity.User;
import com.example.schoolmanagementsystem.exceptions.EmailNotFoundException;
import com.example.schoolmanagementsystem.exceptions.UserNotFoundException;
import com.example.schoolmanagementsystem.service.EmailSenderService;
import com.example.schoolmanagementsystem.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    EmailSenderService emailSenderService;
    //    signing up a user
    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
        // Check if the password and confirmed password match
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(null); // Return bad request status if passwords don't match
        }

        User newUser = userService.signup(signUpRequest);
        return ResponseEntity.ok(newUser);
    }
    //    signing in a user
    @PostMapping("/api/v1/auth/signin")
    public ResponseEntity<String> signin(@RequestBody SignInRequest signInRequest) {
        User user = userService.signin(signInRequest.getUsernameOrEmail(), signInRequest.getPassword());
        if (user != null) {
            // Send email
            emailSenderService.sendEmail(user.getEmail(), "Login Notification", "You have successfully logged in.");
            return ResponseEntity.ok("Signin successful. Check your email for confirmation.");
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    // Forgot password endpoint
    @PostMapping("/api/v1/auth/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail();
        // Check if the email exists in the database
        User user = userService.findByEmail(email);
        if (user != null) {
            // Generate a password reset token and send it to the user's email
            String resetToken = userService.generatePasswordResetToken(user);
            emailSenderService.sendEmail(user.getEmail(), "Password Reset", "Use this link to reset your password: /reset-password?token=" + resetToken);
            return ResponseEntity.ok("Password reset link sent to your email.");
        } else {
            // Throw EmailNotFoundException if the email doesn't exist in the database
            throw new EmailNotFoundException("Email not found.");
        }
    }
        //Getting all users
        @GetMapping("/api/v1/user")
        public ResponseEntity<List<User>> getAllUsers () {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(users);
            }
        }

        //    Getting of user by id
        @GetMapping("/api/v1/user/{id}")
        public ResponseEntity<User> getUserById (@PathVariable Long id){
            User user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        //    Updating of user by id
        @PutMapping("/api/v1/user/{id}")
        public ResponseEntity<User> updateUser (@PathVariable Long id, @RequestBody User updateUser){
            // Ensure the id is set in the updateUser object
            updateUser.setId(id);

            // Call updateUser method and handle the response
            try {
                User updatedUser = userService.updateUser(updateUser);
                return ResponseEntity.ok(updatedUser);
            } catch (UserNotFoundException e) {
                return ResponseEntity.notFound().build();
            }
        }

        //    Deleting of user by id
        @DeleteMapping("/api/v1/user/{id}")
        public ResponseEntity<?> deleteUserById (@PathVariable Long id){
            try {
                userService.deleteUserById(id);
                return ResponseEntity.ok().build();
            } catch (UserNotFoundException e) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage()); // Return the message from the exception
            }
        }
    }
