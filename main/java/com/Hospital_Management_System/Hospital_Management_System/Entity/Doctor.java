package com.Hospital_Management_System.Hospital_Management_System.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(unique = true,nullable = false,length = 100)
    private String email;

    @Column(length = 100)
    private String specialization;

    @ManyToMany(mappedBy = "doctors")
    private Set<Department> departments = new HashSet<>();

    @OneToMany(mappedBy = "doctor",orphanRemoval = true,cascade = {CascadeType.ALL})
    private List<Appointment> appointments = new ArrayList<>();
}
