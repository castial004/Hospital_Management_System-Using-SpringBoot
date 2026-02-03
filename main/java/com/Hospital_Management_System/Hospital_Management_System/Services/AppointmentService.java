package com.Hospital_Management_System.Hospital_Management_System.Services;

import com.Hospital_Management_System.Hospital_Management_System.Dto.AppointmentCreateDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.AppointmentDto;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Appointment;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Doctor;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Patient;
import com.Hospital_Management_System.Hospital_Management_System.Repository.AppointmentRepo;
import com.Hospital_Management_System.Hospital_Management_System.Repository.DoctorRepo;
import com.Hospital_Management_System.Hospital_Management_System.Repository.PatientRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final AppointmentRepo appointmentRepo;

    public List<Long> getPatientAppointmentIds(Long patientId){
        return appointmentRepo.findAppointmentIdsByPatientId(patientId);
    }

    @Transactional
    public AppointmentDto addAppointment(AppointmentCreateDto appointmentCreateDto) {
        Patient patient = patientRepo.findById(appointmentCreateDto.getPatientId()).orElseThrow(() -> new EntityNotFoundException());

        Doctor doctor = doctorRepo.findById(appointmentCreateDto.getDoctorId()).orElseThrow(() -> new EntityNotFoundException());

        Appointment appointment = Appointment.builder()
                .appointmentTime(appointmentCreateDto.getAppointmentTime())
                .reason(appointmentCreateDto.getReason())
                .doctor(doctor)
                .patient(patient)
                .build();

        Appointment savedAppointment =  appointmentRepo.save(appointment);

        // maintain bidirectional consistency
        patient.getAppointments().add(savedAppointment);
        doctor.getAppointments().add(savedAppointment);

        return new AppointmentDto(savedAppointment.getId(), savedAppointment.getReason(), savedAppointment.getAppointmentTime(), patient.getId(), patient.getName(), doctor.getId(), doctor.getName());
    }
    @Transactional
    public AppointmentDto updateDoctorInAppointment(Long id,Long doctorID){
        Appointment appointment = appointmentRepo.findById(id).orElseThrow();
        Doctor doctor =  doctorRepo.findById(doctorID).orElseThrow();

        Doctor oldDoctor = appointment.getDoctor();
        if(oldDoctor!=null){
            oldDoctor.getAppointments().remove(appointment);
        }
        //
        appointment.setDoctor(doctor);//dirty
        doctor.getAppointments().add(appointment);// for bidirectional consistency
        return new AppointmentDto(appointment.getId(), appointment.getReason(),appointment.getAppointmentTime(),appointment.getPatient().getId(),appointment.getPatient().getName(),appointment.getDoctor().getId(),appointment.getDoctor().getName());
    }
    @Transactional
    public AppointmentDto deleteAppointmentById(Long appointmentId){
        Appointment dbAppointment = appointmentRepo.findById(appointmentId).orElseThrow(()->new EntityNotFoundException());
        AppointmentDto appointmentDto =  new AppointmentDto(dbAppointment.getId(), dbAppointment.getReason(), dbAppointment.getAppointmentTime(), dbAppointment.getPatient().getId(),dbAppointment.getPatient().getName(),dbAppointment.getDoctor().getId(),dbAppointment.getDoctor().getName());
        dbAppointment.getPatient().getAppointments().remove(dbAppointment); // orphan removal will remove automatically from appointment db;
        dbAppointment.getDoctor().getAppointments().remove(dbAppointment);
        return appointmentDto;
    }

}
