package com.Hospital_Management_System.Hospital_Management_System.Security;

import com.Hospital_Management_System.Hospital_Management_System.Dto.SignUpResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Entity.AppUser;
import com.Hospital_Management_System.Hospital_Management_System.Dto.LoginRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.LoginResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Repository.AppUserRepo;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService {

    private final AppUserRepo appUserRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;

    public SignUpResponseDto createAppUser(LoginRequestDto userRequestDto){
        AppUser existingAppUser =  appUserRepo.findByUsername(userRequestDto.getUsername()).orElse(null);
        if(existingAppUser!=null){
            throw new EntityExistsException();
        }
        // no such user present therefore create
        AppUser dbAppUser =  appUserRepo.save(AppUser.builder()
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .build());
        return new SignUpResponseDto(dbAppUser.getId(), dbAppUser.getUsername());

    }
    public LoginResponseDto login(LoginRequestDto loginRequestDto){
            Authentication auth =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));
            AppUser appUser = (AppUser) auth.getPrincipal();
            String token = authUtil.getAccessToken(appUser);
            return new LoginResponseDto(token,appUser.getId(), appUser.getUsername());

    }

}
