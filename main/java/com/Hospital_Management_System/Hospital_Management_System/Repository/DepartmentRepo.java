package com.Hospital_Management_System.Hospital_Management_System.Repository;

import com.Hospital_Management_System.Hospital_Management_System.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepo extends JpaRepository<Department,Long>{

    @Query(value = "select dept_id from dept_doctor where doctor_id = :doctorId",nativeQuery = true)
    List<Long> findByDoctorId(@Param("doctorId")Long id);
}
