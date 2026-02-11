package com.Hospital_Management_System.Hospital_Management_System.Contoller;

import com.Hospital_Management_System.Hospital_Management_System.Dto.InsuranceCreateRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.InsuranceResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Services.InsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/insurance")
public class InsuranceController {
    private final InsuranceService insuranceService;
    @GetMapping("/{patientId}")
    public ResponseEntity<InsuranceResponseDto> getInsuranceByPatientId(@PathVariable Long patientId){
            InsuranceResponseDto responseDto = insuranceService.getInsuranceByPatientIdService(patientId);
            return ResponseEntity.ok(responseDto);
    }
    @PostMapping()
    public ResponseEntity<InsuranceResponseDto> createInsurance(@RequestBody InsuranceCreateRequestDto insuranceCreateRequestDto){
        InsuranceResponseDto insuranceResponseDto = insuranceService.setPatientInsurance(insuranceCreateRequestDto);
        return ResponseEntity.ok(insuranceResponseDto);
    }

}
