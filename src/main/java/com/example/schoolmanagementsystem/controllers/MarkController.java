package com.example.schoolmanagementsystem.controllers;

import com.example.schoolmanagementsystem.dto.MarkRequest;
import com.example.schoolmanagementsystem.entity.Mark;
import com.example.schoolmanagementsystem.exceptions.ApiError;
import com.example.schoolmanagementsystem.service.MarkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MarkController {
    private final MarkServiceImpl markService;
    @Autowired
    public MarkController(MarkServiceImpl markService) {
        this.markService = markService;

    }
    @PostMapping("/api/v1/auth/marks")
    public ResponseEntity<?> addMark(@RequestBody MarkRequest markRequest) {
        Mark addMark = markService.addMark(markRequest);
        return new ResponseEntity<>(addMark, HttpStatus.CREATED);
    }
    @GetMapping("/api/v1/auth/marks/{studentId}")
    public ResponseEntity<?> getMarksByStudent(@PathVariable Long studentId) {
        try {
            List<Mark> marks = markService.getMarksByStudent(studentId); // Call the service method to retrieve marks by student
            return ResponseEntity.ok(marks);
        } catch (Exception ex) {
            ApiError apiError = new ApiError(404, HttpStatus.NOT_FOUND, "Failed to retrieve marks: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        }
    }
    @PutMapping("/api/v1/auth/marks/{id}")
    public ResponseEntity<?> updateMark(@PathVariable Long id, @RequestBody MarkRequest markRequest) {
        try {
            Mark updatedMark = markService.updateMark(id, markRequest);
            return ResponseEntity.ok(updatedMark);
        } catch (Exception ex) {
            ApiError apiError = new ApiError(404, HttpStatus.NOT_FOUND, "Failed to update mark: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        }
    }
    @DeleteMapping("/api/v1/auth/marks/{id}")
    public ResponseEntity<?> deleteMark(@PathVariable Long id) {
        try {
            markService.deleteMark(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            ApiError apiError = new ApiError(404, HttpStatus.NOT_FOUND, "Failed to delete mark: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        }
    }
}
