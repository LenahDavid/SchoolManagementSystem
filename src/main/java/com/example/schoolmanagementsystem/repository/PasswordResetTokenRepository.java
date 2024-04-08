package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.PasswordResetToken;
import com.example.schoolmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    void deleteByToken(String token);
    void deleteByUser(User user);
}