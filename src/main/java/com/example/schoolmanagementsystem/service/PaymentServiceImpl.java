package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.PaymentRequest;
import com.example.schoolmanagementsystem.entity.Payment;
import com.example.schoolmanagementsystem.entity.User;
import com.example.schoolmanagementsystem.repository.PaymentRepository;
import com.example.schoolmanagementsystem.repository.StudentRepository;
import com.example.schoolmanagementsystem.repository.UserRepository;
//import io.swagger.v3.oas.models.media.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;
import static io.swagger.v3.oas.models.media.MediaType.*;
import static java.lang.System.currentTimeMillis;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MpesaPaymentService mpesaPaymentService;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Payment addPayment(PaymentRequest paymentRequest) {
        Long studentId = paymentRequest.getStudentId();
        Long amount = paymentRequest.getAmount();

        // Create a new Payment entity
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal(amount));
        payment.setPaymentstatus("Pending");
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        payment.setPaymentDate(new Timestamp(currentDate, null));
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setInvoiceId(paymentRequest.getInvoiceId());

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        payment.setStudent(student);

//        String transactionId = mpesaPaymentService.initiatePayment(paymentRequest);

        // Optionally, update the Payment entity with the transaction ID
//        payment.setTransactionId(transactionId);

            return paymentRepository.save(payment); // Return payment with pending status
        }
//      todo
//        implement integrating the payment

    }

