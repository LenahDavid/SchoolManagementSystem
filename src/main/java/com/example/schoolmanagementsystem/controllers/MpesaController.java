package com.example.schoolmanagementsystem.controllers;

import com.example.schoolmanagementsystem.dto.PaymentRequest;
import com.example.schoolmanagementsystem.dto.StkRequest;
import com.example.schoolmanagementsystem.service.MpesaPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class MpesaController {
    private final MpesaPaymentService mpesaPaymentService;

    @Autowired
    public MpesaController(MpesaPaymentService mpesaPaymentService) {
        this.mpesaPaymentService = mpesaPaymentService;
    }

    @PostMapping("/api/v1/auth/simulatePayment")
    public ResponseEntity<String> simulatePayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            String response = mpesaPaymentService.initiatePayment(
                    paymentRequest.getShortCode(),
                    paymentRequest.getAmount(),
                    paymentRequest.getMsisdn(),
                    paymentRequest.getBillRefNumber(),
                    paymentRequest.getCommandId()

            );
            logger.info("C2B simulate response: {}", response); // Log the response
            return ResponseEntity.ok(response);
        } catch (HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();
            logger.error("Error simulating C2B: {}", responseBody, e); // Log the error
            return ResponseEntity.status(e.getStatusCode())
                    .body("Error simulating C2B: " + responseBody);

        }
    }

    private static final Logger logger = LoggerFactory.getLogger(MpesaController.class);

    @PostMapping("/api/v1/auth/stkpush")
    public ResponseEntity<String> stkpush(@RequestBody StkRequest stkRequest) throws MalformedURLException {
        System.out.println("Received request: " + stkRequest);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime dateTime = LocalDateTime.parse(stkRequest.getTimestamp(), formatter);
            Timestamp timestamp = Timestamp.valueOf(dateTime);

            String response = mpesaPaymentService.stkpush(
                    stkRequest.getBusinessShortCode(),
                    stkRequest.getPassword(),
                    timestamp,
                    stkRequest.getPartyA(),
                    stkRequest.getPartyB(),
                    stkRequest.getPhoneNumber(),
                    stkRequest.getTransactionType(),
                    stkRequest.getAmount(),
                    stkRequest.getAccountReference(),
                    stkRequest.getTransactionDesc(),
                    URI.create(stkRequest.getCallBackUrl()).toURL()

            );

            logger.info("stkpush response: {}", response); // Log the response
            return ResponseEntity.ok(response);

        } catch (HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();
            logger.error("Error simulating stkpush: {}", responseBody, e); // Log the error
            return ResponseEntity.status(e.getStatusCode())
                    .body("Error simulating stkpush: " + responseBody);

        }
    }
}
