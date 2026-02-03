package com.Hospital_Management_System.Hospital_Management_System.Services;

import com.Hospital_Management_System.Hospital_Management_System.CustomException.DuplicateEmailException;
import com.Hospital_Management_System.Hospital_Management_System.Dto.PatientDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.PatientRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Patient;
import com.Hospital_Management_System.Hospital_Management_System.Repository.PatientRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepo patientRepo;
    private final AppointmentService appointmentService;


    public PatientDto createPatient(PatientRequestDto patientDto){
        Optional<Patient> existingPatient =  patientRepo.findByEmail(patientDto.getEmail());
        if(existingPatient.isPresent()){
            throw new DuplicateEmailException("Email already Exist");
        }
        Patient patient = Patient.builder()
                .name(patientDto.getName())
                .email(patientDto.getEmail())
                .birthDate(patientDto.getBirthDate())
                .bloodGroup(patientDto.getBloodGroup())
                .build();
        Patient dbPatient =  patientRepo.save(patient);
        return PatientDto.builder()
                .id(dbPatient.getId())
                .name(dbPatient.getName())
                .email(dbPatient.getEmail())
                .birthDate(dbPatient.getBirthDate())
                .createdAt(dbPatient.getCreatedAt())
                .appointmentIds(null)
                .insuranceId(null)
                .build();
    }

    public PatientDto getPatientById(Long patientId){
        Patient dbPatient =  patientRepo.findById(patientId).orElseThrow(()-> new EntityNotFoundException());

        Long insuranceId = dbPatient.getInsurance()!=null
                ?dbPatient.getInsurance().getId()
                :null;

        return PatientDto.builder()
                .id(dbPatient.getId())
                .name(dbPatient.getName())
                .email(dbPatient.getEmail())
                .birthDate(dbPatient.getBirthDate())
                .createdAt(dbPatient.getCreatedAt())
                .appointmentIds(appointmentService.getPatientAppointmentIds(patientId))
                .insuranceId(insuranceId)
                .build();
    }

    public PatientDto deletePatientById(Long patientId){
        Patient dbPatient =  patientRepo.findById(patientId).orElseThrow(()-> new EntityNotFoundException());
        Long insuranceId = dbPatient.getInsurance()!=null
                ?dbPatient.getInsurance().getId()
                :null;
        List<Long> appointmentIds =  appointmentService.getPatientAppointmentIds(patientId);
        PatientDto patientDto =  PatientDto.builder()
                .id(dbPatient.getId())
                .name(dbPatient.getName())
                .email(dbPatient.getEmail())
                .birthDate(dbPatient.getBirthDate())
                .createdAt(dbPatient.getCreatedAt())
                .appointmentIds(appointmentIds)
                .insuranceId(insuranceId)
                .build();
        patientRepo.deleteById(patientId);
        return patientDto;
    }

    public PatientDto updatePatientById(Long patientId,PatientRequestDto patientRequestDto){
        Patient oldPatient =  patientRepo.findById(patientId).orElseThrow(()-> new EntityNotFoundException());
        if(!oldPatient.getName().equals(patientRequestDto.getName())){
            oldPatient.setName(patientRequestDto.getName());
        }
        if(!oldPatient.getEmail().equals(patientRequestDto.getEmail())){
            oldPatient.setEmail(patientRequestDto.getEmail());
        }
        if(!oldPatient.getBirthDate().equals(patientRequestDto.getBirthDate())){
            oldPatient.setBirthDate(patientRequestDto.getBirthDate());
        }
        if(!oldPatient.getBloodGroup().equals(patientRequestDto.getBloodGroup())){
            oldPatient.setBloodGroup(patientRequestDto.getBloodGroup());
        }
        Patient newPatient =  patientRepo.save(oldPatient);
        Long insuranceId = newPatient.getInsurance()!=null
                ?newPatient.getInsurance().getId()
                :null;
        List<Long> appointmentIds =  appointmentService.getPatientAppointmentIds(patientId);
        return PatientDto.builder()
                .id(newPatient.getId())
                .name(newPatient.getName())
                .email(newPatient.getEmail())
                .birthDate(newPatient.getBirthDate())
                .createdAt(newPatient.getCreatedAt())
                .appointmentIds(appointmentIds)
                .insuranceId(insuranceId)
                .build();
    }
}