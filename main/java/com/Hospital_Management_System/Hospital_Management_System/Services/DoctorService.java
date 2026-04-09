package com.Hospital_Management_System.Hospital_Management_System.Services;

import com.Hospital_Management_System.Hospital_Management_System.Dto.AppointmentResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.DoctorCreateRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.DoctorResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.OnBoardingDoctorDto;
import com.Hospital_Management_System.Hospital_Management_System.Entity.AppUser;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Appointment;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Doctor;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Enums.RoleType;
import com.Hospital_Management_System.Hospital_Management_System.Repository.AppUserRepo;
import com.Hospital_Management_System.Hospital_Management_System.Repository.AppointmentRepo;
import com.Hospital_Management_System.Hospital_Management_System.Repository.DepartmentRepo;
import com.Hospital_Management_System.Hospital_Management_System.Repository.DoctorRepo;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;
    private final DepartmentRepo departmentRepo;
    private final AppUserRepo appUserRepo;

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

    public List<Appointment> getAllAppointmentsDoctor(){
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appointmentRepo.findAllAppointmentDetails(user.getId());
    }

    @Transactional
    public DoctorResponseDto onBoardNewDoctorService(OnBoardingDoctorDto newDoctor){
        // check if user exists or not then only add it
        AppUser user = appUserRepo.findById(newDoctor.getUserId()).orElse(null);
        //also check if doctor with such id exists or not
        if(doctorRepo.existsById(newDoctor.getUserId())){
            throw new EntityExistsException("doctor already exist with this id");
        }
        // else create a new doctor and save
        Doctor newlyCreatedDoctor = Doctor.builder()
                .name(newDoctor.getName())
                .email(newDoctor.getEmail())
                .specialization(newDoctor.getSpecialization())
                .appUser(user)
                .build();

        user.getRoles().add(RoleType.DOCTOR);
        Doctor savedDoctor = doctorRepo.save(newlyCreatedDoctor);
        return DoctorResponseDto.builder()
                .id(savedDoctor.getId())
                .name(savedDoctor.getName())
                .email(savedDoctor.getEmail())
                .specialization(savedDoctor.getSpecialization())
                .build();
    }
}
