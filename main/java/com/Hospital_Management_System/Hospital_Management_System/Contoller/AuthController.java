package com.Hospital_Management_System.Hospital_Management_System.Contoller;

import com.Hospital_Management_System.Hospital_Management_System.Entity.UserRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Entity.UserResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Services.AuthService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> userSignup(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = authService.createAppUser(userRequestDto);
        if(userResponseDto!=null){
            //successfully created a new user
            return ResponseEntity.ok(userResponseDto);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody UserRequestDto userRequestDto, HttpServletRequest httpServletRequest){
        try{
            Authentication auth =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequestDto.getUsername(),userRequestDto.getPassword()));
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            httpServletRequest.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", context);
            //httpServletRequest.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT",auth);
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return ResponseEntity.ok("Login successful: "+userDetail.getUsername());
        }
        catch (Exception e) {
                return ResponseEntity.badRequest().body("Invalid Credentials");
        }
    }
}
