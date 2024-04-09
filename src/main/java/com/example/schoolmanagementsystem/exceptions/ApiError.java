package com.example.schoolmanagementsystem.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
public class ApiError {
    private int code; // Status code
    @Getter
    @Setter
    private HttpStatus status;
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime localDateTime = LocalDateTime.now();
    @Setter
    @Getter
    private String message;

    public ApiError(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }


    public ApiError(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

//    public LocalDateTime getLocalDateTime() {
//        return localDateTime;
//    }

}
