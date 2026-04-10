package com.Hospital_Management_System.Hospital_Management_System.Contoller.Admin_Controller;

import com.Hospital_Management_System.Hospital_Management_System.Dto.DoctorResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.OnBoardingDoctorDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.PatientDto;
import com.Hospital_Management_System.Hospital_Management_System.Services.DoctorService;
import com.Hospital_Management_System.Hospital_Management_System.Services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final DoctorService doctorService;
    private final PatientService patientService;

    @PostMapping("/onBoardDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnBoardingDoctorDto newDoctor){
        DoctorResponseDto savedDoctor =  doctorService.onBoardNewDoctorService(newDoctor);
        return ResponseEntity.ok(savedDoctor);
    }
    @GetMapping
    public ResponseEntity<String> check(){
        return ResponseEntity.ok("ok");
    }
    @GetMapping("/get/doctor/{doctorId}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long doctorId){
        DoctorResponseDto doctorResponseDto =  doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctorResponseDto);
    }

    @GetMapping("/get/patient/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id){
        try{
            PatientDto patientDto =  patientService.getPatientById(id);
            return ResponseEntity.ok(patientDto);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
