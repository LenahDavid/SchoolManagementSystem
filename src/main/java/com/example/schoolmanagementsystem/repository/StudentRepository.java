package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.Student;
import com.example.schoolmanagementsystem.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findById(Long id);
//    Subject findByName(String name);
}
