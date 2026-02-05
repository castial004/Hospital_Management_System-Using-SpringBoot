package com.Hospital_Management_System.Hospital_Management_System.Contoller;

import com.Hospital_Management_System.Hospital_Management_System.Dto.LoginRequestDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.LoginResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Dto.SignUpResponseDto;
import com.Hospital_Management_System.Hospital_Management_System.Security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> userSignup(@RequestBody LoginRequestDto loginRequestDto){
        SignUpResponseDto signUpDbUser = authService.createAppUser(loginRequestDto);
        return ResponseEntity.ok(signUpDbUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginRequestDto loginRequestDto){
            LoginResponseDto responseDto = authService.login(loginRequestDto);
            return ResponseEntity.ok(responseDto);
    }
}
//Earlier i was using session for authentication
//    @PostMapping("/login")
//    public ResponseEntity<String> userLogin(@RequestBody LoginRequestDto userRequestDto, HttpServletRequest httpServletRequest){
//        try{
//            Authentication auth =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequestDto.getUsername(),userRequestDto.getPassword()));
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            context.setAuthentication(auth);
//            httpServletRequest.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", context);
//            //httpServletRequest.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT",auth);
//            UserDetails userDetail = (UserDetails) auth.getPrincipal();
//            return ResponseEntity.ok("Login successful: "+userDetail.getUsername());
//        }
//        catch (Exception e) {
//                return ResponseEntity.badRequest().body("Invalid Credentials");
//        }
//    }
