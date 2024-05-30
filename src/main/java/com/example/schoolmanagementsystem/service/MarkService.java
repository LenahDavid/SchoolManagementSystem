package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.MarkRequest;
import com.example.schoolmanagementsystem.entity.Mark;

public interface MarkService {
    Mark addMark(MarkRequest markRequest);
    Mark updateMark(Long id, MarkRequest markRequest);
    void deleteMark(Long id);
}
