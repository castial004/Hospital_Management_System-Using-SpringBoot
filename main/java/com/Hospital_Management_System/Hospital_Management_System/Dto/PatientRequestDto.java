package com.Hospital_Management_System.Hospital_Management_System.Dto;

import com.Hospital_Management_System.Hospital_Management_System.Entity.Enums.BloodGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientRequestDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Email
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Format YYYY-MM-DD And cannot be empty")
    private LocalDate birthDate;
    @NotBlank(message = "Format A_Positive or A_Negative etc...")
    private BloodGroup bloodGroup;
}
