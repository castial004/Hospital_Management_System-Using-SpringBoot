package com.Hospital_Management_System.Hospital_Management_System.Dto;


import com.Hospital_Management_System.Hospital_Management_System.Entity.Enums.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@Builder
public class PatientDto {

    private Long id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private BloodGroup bloodGroup;
    private LocalDateTime createdAt;
    private Long insuranceId;
    private List<Long> appointmentIds;
}
