package com.Hospital_Management_System.Hospital_Management_System.Repository;

import com.Hospital_Management_System.Hospital_Management_System.Entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuranceRepo extends JpaRepository<Insurance,Long> {

    @Query("select i from Insurance i where i.patient.id = :patientId")
    Optional<Insurance> findByPatientId(@Param("patientId") Long patientId);
}
