package com.Hospital_Management_System.Hospital_Management_System.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false,length = 50)
    private String name;

    @OneToOne
    @JoinColumn(nullable = false)
    private Doctor headDoctor;


    @ManyToMany
    @JoinTable(
            name = "dept_doctor",
            joinColumns = {@JoinColumn(name = "dept_id")},
            inverseJoinColumns = {@JoinColumn(name = "doctor_id")}
    )
    private Set<Doctor> doctors = new HashSet<>();
}
