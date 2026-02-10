package com.Hospital_Management_System.Hospital_Management_System.Dto;

import com.Hospital_Management_System.Hospital_Management_System.Entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponseDto {
    private Long id;
    private String name;
    private String email;
    private String specialization;
    private List<Long> appointmentIds;
    private List<Long> departmentIds;
}
