package com.example.schoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.security.Timestamp;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="transaction_id")
    private String transactionId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_status")
    private String paymentstatus;

    @Column(name = "payment_date")
    private Timestamp paymentDate;
    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "invoice_id")
    private String invoiceId;


    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private User student;


    }

