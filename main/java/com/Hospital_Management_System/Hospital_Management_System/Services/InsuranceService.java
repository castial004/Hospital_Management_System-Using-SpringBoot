package com.Hospital_Management_System.Hospital_Management_System.Services;

import java.util.ArrayList;
import java.util.List;

import com.Hospital_Management_System.Hospital_Management_System.Dto.InsuranceCreateRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.InsuranceResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.PatientDto;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Appointment;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Insurance;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Patient;
import com.Hospital_Management_System.Hospital_Management_System.Repository.InsuranceRepo;
import com.Hospital_Management_System.Hospital_Management_System.Repository.PatientRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class InsuranceService {
    private final InsuranceRepo insuranceRepo;
    private final PatientRepo patientRepo;


    @Transactional
    public InsuranceResponseDto setPatientInsurance(InsuranceCreateRequestDto insuranceCreateRequestDto){
        Patient patient = patientRepo.findById(insuranceCreateRequestDto.getPatientId()).orElseThrow(()->new EntityNotFoundException("Invalid patient id: "));
            Insurance newInsurance = Insurance.builder()
                    .patient(patient)
                    .provider(insuranceCreateRequestDto.getProvider())
                    .policyNumber(insuranceCreateRequestDto.getPolicyNumber())
                    .validUntil(insuranceCreateRequestDto.getValidUntil())
                    .build();
            //insuranceRepo.save(insurance); no need jpa will do because of cascade.all
            patient.setInsurance(newInsurance);
            //insurance.setPatient(patient);// for bi direction consistency
            patientRepo.flush();
            return InsuranceResponseDto.builder()
                    .InsuranceId(newInsurance.getId())
                    .patientId(newInsurance.getPatient().getId())
                    .createdAt(newInsurance.getCreatedAt())
                    .provider(newInsurance.getProvider())
                    .policyNumber(newInsurance.getPolicyNumber())
                    .validUntil(newInsurance.getValidUntil())
                    .build();
    }
    // make better
    @Transactional
    public PatientDto removeInsuranceFromPatient(Long patientId){
        Patient patient =  patientRepo.findById(patientId).orElseThrow(()-> new EntityNotFoundException("Invalid patient id: "));
        patient.setInsurance(null);//dirty
        List<Appointment> appointmentList = patient.getAppointments();
        List<Long> appointmentIds = new ArrayList<>();
        for(Appointment a: appointmentList){
            appointmentIds.add(a.getId());
        }
        return new PatientDto(patient.getId(),patient.getName(),patient.getEmail(),patient.getBirthDate(),patient.getBloodGroup(),patient.getCreatedAt(),null,appointmentIds);
    }
    public InsuranceResponseDto getInsuranceByPatientIdService(Long patientId){
        Insurance dbInsurance =  insuranceRepo.findByPatientId(patientId).orElseThrow(()-> new EntityNotFoundException());
        return InsuranceResponseDto.builder()
                .InsuranceId(dbInsurance.getId())
                .patientId(dbInsurance.getPatient().getId())
                .policyNumber(dbInsurance.getPolicyNumber())
                .provider(dbInsurance.getProvider())
                .createdAt(dbInsurance.getCreatedAt())
                .validUntil(dbInsurance.getValidUntil())
                .build();
    }
}
