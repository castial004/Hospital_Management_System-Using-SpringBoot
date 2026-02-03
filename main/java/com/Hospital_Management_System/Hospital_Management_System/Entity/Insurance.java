package com.Hospital_Management_System.Hospital_Management_System.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate validUntil;

    @Column(nullable = false,length = 100)
    private String provider;

    @Column(nullable = false,length = 50,unique = true)
    private String policyNumber;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "insurance")
    private Patient patient;
}
