package com.Hospital_Management_System.Hospital_Management_System.Repository;

import com.Hospital_Management_System.Hospital_Management_System.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {
    Optional<Patient> findByEmail(String email);
    List<Patient> findByName(String name);

    @Query(value = "select blood_group,count(id) from patient  group by blood_group", nativeQuery = true)
    List<Object[]> countOfBloodGroup();

    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments a LEFT JOIN FETCH a.doctor")
    List<Patient> findAllWithAppointment();
}
