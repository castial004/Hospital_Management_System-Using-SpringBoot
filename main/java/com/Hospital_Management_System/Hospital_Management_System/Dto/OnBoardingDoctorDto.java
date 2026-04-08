package com.Hospital_Management_System.Hospital_Management_System.Dto;

import lombok.Data;

@Data
public class OnBoardingDoctorDto {
    private Long userId;
    private String name;
    private String email;
    private String specialization;
}
