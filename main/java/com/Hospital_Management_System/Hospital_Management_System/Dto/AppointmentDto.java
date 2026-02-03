package com.Hospital_Management_System.Hospital_Management_System.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppointmentDto {
    private Long id;
    private String reason;
    private LocalDateTime appointmentTime;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
}
