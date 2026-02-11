package com.Hospital_Management_System.Hospital_Management_System.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InsuranceDto {
    private Long InsuranceId;
    private Long patientId;
    private String provider;
    private String policyNumber;
    private LocalDate validUntil;
    private LocalDateTime createdAt;
}

