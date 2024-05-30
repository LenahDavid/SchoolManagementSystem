package com.example.schoolmanagementsystem.controllers;

import com.example.schoolmanagementsystem.service.MpesaGenerateToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/mpesa")
public class GenerateToken {


        @GetMapping("/generateToken")
        public String generateToken() {
            MpesaGenerateToken mpesaGenerateToken = new MpesaGenerateToken();
            return mpesaGenerateToken.getResponse();
        }

    }

