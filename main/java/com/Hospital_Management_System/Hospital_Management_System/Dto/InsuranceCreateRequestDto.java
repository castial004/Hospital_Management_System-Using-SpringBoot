package com.Hospital_Management_System.Hospital_Management_System.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceCreateRequestDto {
    private Long patientId;
    private String provider;
    private String policyNumber;
    private LocalDate validUntil;
}
