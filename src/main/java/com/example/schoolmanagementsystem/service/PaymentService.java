package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.PaymentRequest;
import com.example.schoolmanagementsystem.entity.Payment;

public interface PaymentService {

    Payment addPayment(PaymentRequest paymentRequest);
}
