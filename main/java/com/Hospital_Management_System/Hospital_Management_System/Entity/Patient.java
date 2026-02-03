package com.Hospital_Management_System.Hospital_Management_System.Entity;


import com.Hospital_Management_System.Hospital_Management_System.Entity.Enums.BloodGroup;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Table(
//        uniqueConstraints = {@UniqueConstraint(name = "no same name and email",columnNames = {"name","email"})}
//)
@ToString
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private LocalDate birthDate;

    @CreationTimestamp
//    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @OneToOne(cascade ={CascadeType.ALL},orphanRemoval = true )
    @JoinColumn
    private Insurance insurance;

    @OneToMany(mappedBy = "patient",cascade = {CascadeType.ALL},orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();
}

