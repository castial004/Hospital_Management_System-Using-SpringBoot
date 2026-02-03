package com.Hospital_Management_System.Hospital_Management_System.Repository;

import com.Hospital_Management_System.Hospital_Management_System.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    @Query(value = "select id from appointment where patient_id = :patientId",nativeQuery = true)
    List<Long> findAppointmentIdsByPatientId(@Param("patientId") Long id);
}