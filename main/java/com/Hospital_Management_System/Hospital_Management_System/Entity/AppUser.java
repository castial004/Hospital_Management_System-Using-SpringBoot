package com.Hospital_Management_System.Hospital_Management_System.Entity;

import com.Hospital_Management_System.Hospital_Management_System.Entity.Enums.AuthProviderType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {@Index(name="provider_Id_provider_Type",columnList = "providerId,authProviderType",unique = false)})
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String username;
//    @Column(nullable = false)
    private String password;

    private String providerId;
    @Enumerated(EnumType.STRING)
    private AuthProviderType authProviderType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
