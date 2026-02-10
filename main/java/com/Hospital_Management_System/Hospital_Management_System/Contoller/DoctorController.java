package com.Hospital_Management_System.Hospital_Management_System.Contoller;

import com.Hospital_Management_System.Hospital_Management_System.Dto.DoctorCreateRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.DoctorResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Services.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
@AllArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    @PostMapping()
    public ResponseEntity<DoctorResponseDto> addDoctor(@RequestBody DoctorCreateRequestDto doctorCreateRequestDto){
        DoctorResponseDto doctorResponseDto =  doctorService.addDoctorService(doctorCreateRequestDto);
        return ResponseEntity.ok(doctorResponseDto);
    }
    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long doctorId){
        DoctorResponseDto doctorResponseDto =  doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctorResponseDto);
    }
}
