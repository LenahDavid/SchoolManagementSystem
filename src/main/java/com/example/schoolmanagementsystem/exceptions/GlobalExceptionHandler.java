package com.example.schoolmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        ApiError apiError = new ApiError(404,HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UserNotFoundException exception) {
        ApiError apiError = new ApiError(404,HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Object> handleEmailNotFoundException(UserNotFoundException exception) {
        ApiError apiError = new ApiError(404,HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(PasswordCheckingException.class)
    public ResponseEntity<Object> handlePasswordCheckingException(PasswordCheckingException exception) {
        ApiError apiError = new ApiError(400,HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(PasswordDoesnotMatchException.class)
    public ResponseEntity<Object> handlePasswordDoesnotMatchException(PasswordDoesnotMatchException exception) {
        ApiError apiError = new ApiError(400, HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.ok(apiError);
    }


}

