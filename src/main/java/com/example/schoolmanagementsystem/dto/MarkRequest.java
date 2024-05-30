package com.example.schoolmanagementsystem.dto;

import lombok.Data;

@Data
public class MarkRequest {
    private  double markValue;
    private Long subjectId;
    private Long studentId;
}
