package com.Hospital_Management_System.Hospital_Management_System.Services;

import com.Hospital_Management_System.Hospital_Management_System.Dto.DoctorCreateRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.DoctorResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Doctor;
import com.Hospital_Management_System.Hospital_Management_System.Repository.AppointmentRepo;
import com.Hospital_Management_System.Hospital_Management_System.Repository.DepartmentRepo;
import com.Hospital_Management_System.Hospital_Management_System.Repository.DoctorRepo;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;
    private final DepartmentRepo departmentRepo;
    public DoctorResponseDto addDoctorService(DoctorCreateRequestDto doctorCreateRequestDto){
        Doctor dbExistingDoctor = doctorRepo.findByEmail(doctorCreateRequestDto.getEmail()).orElse(null);
        if(dbExistingDoctor==null){
            Doctor newDbDoctor =  doctorRepo.save(Doctor.builder()
                    .name(doctorCreateRequestDto.getName())
                    .email(doctorCreateRequestDto.getEmail())
                    .specialization(doctorCreateRequestDto.getSpecialization()).build());
            return DoctorResponseDto.builder()
                    .id(newDbDoctor.getId())
                    .name(newDbDoctor.getName())
                    .email(newDbDoctor.getEmail())
                    .specialization(newDbDoctor.getSpecialization())
                    .appointmentIds(null)
                    .departmentIds(null)
                    .build();
        }
        throw new EntityExistsException("Email Already Exists");
    }

    public DoctorResponseDto getDoctorById(Long id){
        Doctor dbDoctor = doctorRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Doctor not found"));
        List<Long> appointmentIds = appointmentRepo.findAppointmentIdsByDoctorId(id);
        List<Long> departmentIds = departmentRepo.findByDoctorId(id);
        return DoctorResponseDto.builder()
                .id(dbDoctor.getId())
                .name(dbDoctor.getName())
                .email(dbDoctor.getEmail())
                .specialization(dbDoctor.getSpecialization())
                .appointmentIds(appointmentIds)
                .departmentIds(departmentIds)
                .build();
    }
}
