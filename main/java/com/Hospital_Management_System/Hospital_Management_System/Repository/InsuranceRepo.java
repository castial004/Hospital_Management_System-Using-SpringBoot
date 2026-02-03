package com.Hospital_Management_System.Hospital_Management_System.Repository;

import com.Hospital_Management_System.Hospital_Management_System.Entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepo extends JpaRepository<Insurance,Long> {

}
