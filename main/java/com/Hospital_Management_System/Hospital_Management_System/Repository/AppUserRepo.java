package com.Hospital_Management_System.Hospital_Management_System.Repository;

import com.Hospital_Management_System.Hospital_Management_System.Entity.AppUser;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Enums.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByProviderIdAndAuthProviderType(String providerId, AuthProviderType authProviderType);

//    @Query("select a from AppUser a where a.providerId = :id and a.authProviderType = :type")
//    Optional<AppUser> findByProviderIdAndAuthProviderType(@Param("id")String id, @Param("type")AuthProviderType type);

}
