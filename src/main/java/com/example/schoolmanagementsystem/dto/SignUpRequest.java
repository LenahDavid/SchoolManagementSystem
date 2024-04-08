package com.example.schoolmanagementsystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SignUpRequest {

    private  String firstName;
    private String lastName;
    private String username;
    private  String email;
    private String password;
    private String confirmPassword;
    private String role;
}
