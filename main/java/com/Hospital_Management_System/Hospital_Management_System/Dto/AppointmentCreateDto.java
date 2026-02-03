package com.Hospital_Management_System.Hospital_Management_System.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor@AllArgsConstructor
public class AppointmentCreateDto {
    private String reason;
    private LocalDateTime appointmentTime;
    private Long patientId;
    private Long doctorId;
}
