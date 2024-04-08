package com.example.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class SignInRequest {

    private String usernameOrEmail;
    private String password;


}
