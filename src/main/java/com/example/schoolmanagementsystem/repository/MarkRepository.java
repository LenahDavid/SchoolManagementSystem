package com.example.schoolmanagementsystem.repository;

import com.example.schoolmanagementsystem.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository <Mark, Long> {
    Mark findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    List<Mark> findByStudentId(Long studentId);
    Optional<Mark> findById(Long id);

}

