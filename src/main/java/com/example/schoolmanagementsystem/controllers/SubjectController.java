package com.example.schoolmanagementsystem.controllers;

import com.example.schoolmanagementsystem.dto.SubjectRequest;
import com.example.schoolmanagementsystem.entity.Subject;
import com.example.schoolmanagementsystem.exceptions.ApiError;
import com.example.schoolmanagementsystem.exceptions.SubjectNotFoundException;
import com.example.schoolmanagementsystem.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/api/v1/auth/subjects")
    public ResponseEntity<Subject> createSubject(@RequestBody SubjectRequest subjectRequest) {
        Subject createdSubject = subjectService.addSubject(subjectRequest);
        return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/auth/subjects")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/api/v1/auth/subject/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        try {
            Subject subject = subjectService.findById(id);
            return ResponseEntity.ok(subject);
        } catch (SubjectNotFoundException ex) {
            ApiError apiError = new ApiError(404, HttpStatus.NOT_FOUND, ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(apiError);
        }
    }

    @PutMapping("/api/v1/auth/subject/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable Long id, @RequestBody SubjectRequest subjectRequest) {
        try {
            Subject updatedSubject = subjectService.updateSubject(id, subjectRequest);
            return ResponseEntity.ok(updatedSubject);
        } catch (SubjectNotFoundException ex) {
            ApiError apiError = new ApiError(404, HttpStatus.NOT_FOUND, ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(apiError);
        }
    }
    @DeleteMapping("/api/v1/auth/subject/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
       try{ subjectService.deleteSubject(id);
        return ResponseEntity.ok().build();
    }catch (SubjectNotFoundException ex) {
        ApiError apiError = new ApiError(404, HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiError);
    }
    }
}