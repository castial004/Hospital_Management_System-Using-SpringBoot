package com.Hospital_Management_System.Hospital_Management_System.Contoller.Admin_Controller;

import com.Hospital_Management_System.Hospital_Management_System.Dto.DoctorResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.OnBoardingDoctorDto;
import com.Hospital_Management_System.Hospital_Management_System.Services.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final DoctorService doctorService;

    @PostMapping("/onBoardDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnBoardingDoctorDto newDoctor){
        DoctorResponseDto savedDoctor =  doctorService.onBoardNewDoctorService(newDoctor);
        return ResponseEntity.ok(savedDoctor);
    }
    @GetMapping
    public ResponseEntity<String> check(){
        return ResponseEntity.ok("ok");
    }
}
