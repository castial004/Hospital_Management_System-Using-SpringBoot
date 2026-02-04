package com.Hospital_Management_System.Hospital_Management_System.Services;

import com.Hospital_Management_System.Hospital_Management_System.Entity.AppUser;
import com.Hospital_Management_System.Hospital_Management_System.Dto.UserRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.UserResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Repository.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;
    public UserResponseDto createAppUser(UserRequestDto userRequestDto){
        Optional<AppUser> optionalAppUser =  appUserRepo.findByUsername(userRequestDto.getUsername());
        if(optionalAppUser.isEmpty()){
            // no such user present therefore create
            AppUser dbAppUser =  appUserRepo.save(AppUser.builder()
                    .username(userRequestDto.getUsername())
                    .password(passwordEncoder.encode(userRequestDto.getPassword()))
                    .build());
            return new UserResponseDto(dbAppUser.getId(), dbAppUser.getUsername());
        }
        return null;
    }

}
