package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.SubjectRequest;
import com.example.schoolmanagementsystem.entity.Subject;

import java.util.List;

public interface SubjectService {
    Subject addSubject(SubjectRequest subjectRequest);
    Subject findSubjectByName(String name);

    List<Subject> getAllSubjects();
    Subject findById(Long id);
    Subject updateSubject(Long id, SubjectRequest subjectRequest);
    void deleteSubject(Long id);

}