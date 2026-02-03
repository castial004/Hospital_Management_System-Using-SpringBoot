package com.Hospital_Management_System.Hospital_Management_System.Contoller;

import com.Hospital_Management_System.Hospital_Management_System.Dto.AppointmentCreateDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.AppointmentDto;
import com.Hospital_Management_System.Hospital_Management_System.Services.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping()
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentCreateDto appointmentCreateDto){
        try{
            AppointmentDto appointmentDto =  appointmentService.addAppointment(appointmentCreateDto);
            return ResponseEntity.ok(appointmentDto);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{appointmentId}")
    public ResponseEntity<AppointmentDto> deleteAppointmentById(@PathVariable Long appointmentId){
        try{
            AppointmentDto appointmentDto =  appointmentService.deleteAppointmentById(appointmentId);
            return ResponseEntity.ok(appointmentDto);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}