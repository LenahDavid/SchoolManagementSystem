package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.Mark;
import com.example.schoolmanagementsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentId(Long studentId);
    Optional<Payment> findById(Long id);
}
