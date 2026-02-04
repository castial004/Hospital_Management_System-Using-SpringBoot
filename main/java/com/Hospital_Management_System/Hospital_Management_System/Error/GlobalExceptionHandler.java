package com.Hospital_Management_System.Hospital_Management_System.Error;

import com.Hospital_Management_System.Hospital_Management_System.CustomException.DuplicateEmailException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtError(JwtException jex){
        ApiError apiError = new ApiError("Invalid jwt session"+ jex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(apiError);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(EntityNotFoundException entityNotFoundException){
        ApiError apiError = new ApiError(entityNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiError> handleEmailDuplicate(DuplicateEmailException duplicateEmailException){
        ApiError apiError = new ApiError(duplicateEmailException.getMessage(),HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(apiError);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFound(UsernameNotFoundException usernameNotFoundException){
        ApiError apiError = new ApiError(usernameNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }
}
