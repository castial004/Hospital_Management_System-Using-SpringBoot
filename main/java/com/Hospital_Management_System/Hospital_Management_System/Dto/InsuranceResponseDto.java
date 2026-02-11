package com.Hospital_Management_System.Hospital_Management_System.Dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InsuranceResponseDto {
    private Long InsuranceId;
    private Long patientId;
    private String provider;
    private String policyNumber;
    private LocalDate validUntil;
    private LocalDateTime createdAt;
}

