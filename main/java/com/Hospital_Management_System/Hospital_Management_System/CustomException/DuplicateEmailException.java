package com.Hospital_Management_System.Hospital_Management_System.CustomException;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String message){
        super(message);
    }
}
