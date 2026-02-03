package com.Hospital_Management_System.Hospital_Management_System.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.Hospital_Management_System.Hospital_Management_System.Dto.InsuranceDto;
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
    public InsuranceDto setPatientInsurance(Insurance insurance, Long patientId){
        Optional<Patient> patientOptional = patientRepo.findById(patientId);
        if(patientOptional.isPresent()){
            Patient patient =  patientOptional.get();
            //insuranceRepo.save(insurance); no need jpa will do because of cascade.all
            patient.setInsurance(insurance);
            insurance.setPatient(patient);// for bi direction consistency
            patientRepo.flush();
            return new InsuranceDto(insurance.getId(),insurance.getPatient().getId(),insurance.getProvider(),insurance.getPolicyNumber(),insurance.getValidUntil(),insurance.getCreatedAt());
        }
        return null;
    }
    // make better
    @Transactional
    public PatientDto removeInsuranceFromPatient(Long patientId){
        Patient patient =  patientRepo.findById(patientId).orElseThrow();
        patient.setInsurance(null);//dirty
        List<Appointment> appointmentList = patient.getAppointments();
        List<Long> appointmentIds = new ArrayList<>();
        for(Appointment a: appointmentList){
            appointmentIds.add(a.getId());
        }
        return new PatientDto(patient.getId(),patient.getName(),patient.getEmail(),patient.getBirthDate(),patient.getBloodGroup(),patient.getCreatedAt(),null,appointmentIds);
    }
}
