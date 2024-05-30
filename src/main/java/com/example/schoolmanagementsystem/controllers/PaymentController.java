package com.example.schoolmanagementsystem.controllers;

import com.example.schoolmanagementsystem.dto.PaymentRequest;
import com.example.schoolmanagementsystem.entity.Payment;
import com.example.schoolmanagementsystem.exceptions.ApiError;
import com.example.schoolmanagementsystem.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final PaymentServiceImpl paymentService;

    @Autowired
    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/api/v1/auth/makepayment")
    public ResponseEntity<?> addPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            Payment payment = paymentService.addPayment(paymentRequest);
            return ResponseEntity.ok(payment);
        } catch (IllegalArgumentException e) {
            ApiError apiError = new ApiError(400, HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
        } catch (Exception e) {
            ApiError apiError = new ApiError(400, HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
        }
    }
}