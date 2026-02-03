package com.Hospital_Management_System.Hospital_Management_System.Contoller;

import com.Hospital_Management_System.Hospital_Management_System.CustomException.DuplicateEmailException;
import com.Hospital_Management_System.Hospital_Management_System.Dto.PatientDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.PatientRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    //get patient by id
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id){
        try{
            PatientDto patientDto =  patientService.getPatientById(id);
            return ResponseEntity.ok(patientDto);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Add patient
    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientRequestDto patientRequestDto){
        try{
            PatientDto newPatientDto =  patientService.createPatient(patientRequestDto);
            return new ResponseEntity<>(newPatientDto,HttpStatus.CREATED);
        } catch (DuplicateEmailException d) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<PatientDto> deletePatientById(@PathVariable Long patientId){
        try{
            PatientDto patientDto =  patientService.deletePatientById(patientId);
            return ResponseEntity.ok(patientDto);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{patientId}")
    public ResponseEntity<PatientDto> updateStudentDetails(@PathVariable Long patientId,@RequestBody PatientRequestDto patientRequestDto){
        try{
            PatientDto patientDto =  patientService.updatePatientById(patientId,patientRequestDto);
            return ResponseEntity.ok(patientDto);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}