package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByName(String name);
}