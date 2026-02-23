package com.Hospital_Management_System.Hospital_Management_System.Security;

import com.Hospital_Management_System.Hospital_Management_System.Dto.SignUpResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Entity.AppUser;
import com.Hospital_Management_System.Hospital_Management_System.Dto.LoginRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.LoginResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Enums.AuthProviderType;
import com.Hospital_Management_System.Hospital_Management_System.Repository.AppUserRepo;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class AuthService {

    private final AppUserRepo appUserRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;

    //our oauth login calls this
    public AppUser signup(LoginRequestDto loginRequestDto, AuthProviderType authProviderType,String providerId){
        AppUser appUser =  appUserRepo.findByUsername(loginRequestDto.getUsername()).orElse(null);
        if(appUser!=null){
            throw new EntityExistsException();
        }
        // no such user present therefore create
        appUser = AppUser.builder()
                .username(loginRequestDto.getUsername())
                .providerId(providerId)
                .authProviderType(authProviderType)
                .build();
        if(authProviderType == AuthProviderType.EMAIL){
                appUser.setPassword(passwordEncoder.encode(loginRequestDto.getPassword()));
        }
        return appUserRepo.save(appUser);
    }
    //controller call this
    public SignUpResponseDto createAppUser(LoginRequestDto loginRequestDto){
        AppUser dbAppUser = signup(loginRequestDto,AuthProviderType.EMAIL,null);
        return new SignUpResponseDto(dbAppUser.getId(), dbAppUser.getUsername());

    }
    public LoginResponseDto login(LoginRequestDto loginRequestDto){
            Authentication auth =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));
            AppUser appUser = (AppUser) auth.getPrincipal();
            String token = authUtil.getAccessToken(appUser);
            return new LoginResponseDto(token,appUser.getId(), appUser.getUsername());

    }
    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuthLogin(OAuth2User auth2User,String registrationId) {
        AuthProviderType authProviderType =  authUtil.getAuthProviderFromRegistrationId(registrationId);
        String providerId = authUtil.getProviderId(auth2User,registrationId);

        AppUser appUser = appUserRepo.findByProviderIdAndAuthProviderType(providerId,authProviderType).orElse(null);
        String email = auth2User.getAttribute("email");
        AppUser existingEmailUser = appUserRepo.findByUsername(email).orElse(null);

        if(appUser==null && existingEmailUser==null){
            //create a new user
            //which to use email or provider id
            String username = authUtil.determineUserNameFromOAuth2Login(auth2User,providerId,registrationId);
            appUser = signup(new LoginRequestDto(username,null),authProviderType,providerId); // updating above app user
        } else if (appUser!=null && email!=null && !email.isBlank() && email!= appUser.getUsername()) {
            //we got access of email before this we were storing some name not email
            appUser.setUsername(email);
            appUserRepo.save(appUser);
        } else{
            //user already exist with email but now using different vendor to login
            throw new BadCredentialsException("This email already exits and registered with provider: "+existingEmailUser.getAuthProviderType());
        }
        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.getAccessToken(appUser), appUser.getId(), appUser.getUsername());
        return ResponseEntity.ok(loginResponseDto);
    }
}
