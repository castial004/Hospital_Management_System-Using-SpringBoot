package com.Hospital_Management_System.Hospital_Management_System.Dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDto {
    private String jwt;
    private Long id;
    private String username;
}
