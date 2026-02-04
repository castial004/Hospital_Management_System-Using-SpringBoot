package com.Hospital_Management_System.Hospital_Management_System.Error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private LocalDateTime localDateTime;
    private String Error;
    private HttpStatus httpStatusCode;


    public ApiError(String error,HttpStatus status){
        this(LocalDateTime.now(),error,status);
    }
}
