package com.Hospital_Management_System.Hospital_Management_System.Repository;

import com.Hospital_Management_System.Hospital_Management_System.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findByEmail(String email);
}