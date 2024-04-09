package com.example.schoolmanagementsystem.exceptions;

public class PasswordDoesnotMatchException extends RuntimeException {
    public PasswordDoesnotMatchException(String message) {
        super(message);
    }
}
