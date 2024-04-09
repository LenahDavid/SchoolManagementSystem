package com.example.schoolmanagementsystem.controllers;

import com.example.schoolmanagementsystem.dto.ForgotPasswordRequest;
import com.example.schoolmanagementsystem.dto.SignInRequest;
import com.example.schoolmanagementsystem.dto.SignUpRequest;
import com.example.schoolmanagementsystem.entity.User;
import com.example.schoolmanagementsystem.exceptions.*;
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
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        try {
            User newUser = userService.signup(signUpRequest); // Pass SignUpRequest and role
            return ResponseEntity.ok(newUser);
        } catch (PasswordCheckingException ex) {
            ApiError apiError = new ApiError(400, HttpStatus.BAD_REQUEST, ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(apiError);
        } catch (PasswordDoesnotMatchException ex) {
            ApiError apiError = new ApiError(400, HttpStatus.BAD_REQUEST, ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(apiError);
        }
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
        public ResponseEntity<?> getUserById(@PathVariable Long id) {
            try {
                User user = userService.getUserById(id);
                return ResponseEntity.ok(user);
            } catch (UserNotFoundException ex) {
                ApiError apiError = new ApiError(404, HttpStatus.NOT_FOUND, ex.getMessage());
                return ResponseEntity.ok(apiError);
            }
        }

        //    Updating of user by id
        @PutMapping("/api/v1/user/{id}")
        public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updateUser) {
            // Ensure the id is set in the updateUser object
            updateUser.setId(id);
            // Call updateUser method and handle the response
            try {
                User updatedUser = userService.updateUser(updateUser);
                return ResponseEntity.ok(updatedUser);
            } catch (IllegalArgumentException ex) {
                ApiError apiError = new ApiError(400, HttpStatus.BAD_REQUEST, ex.getMessage());
                return ResponseEntity.status(HttpStatus.OK).body(apiError);
            } catch (UserNotFoundException ex) {
                ApiError apiError = new ApiError(404, HttpStatus.NOT_FOUND, ex.getMessage());
                return ResponseEntity.status(HttpStatus.OK).body(apiError);
            }
        }
    //    Deleting of user by id
    @DeleteMapping("/api/v1/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException ex) {
            ApiError apiError = new ApiError(404, HttpStatus.NOT_FOUND, ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(apiError);
        }
    }
}
