package com.Hospital_Management_System.Hospital_Management_System.Security;

import com.Hospital_Management_System.Hospital_Management_System.Entity.AppUser;
import com.Hospital_Management_System.Hospital_Management_System.Repository.AppUserRepo;
import com.Hospital_Management_System.Hospital_Management_System.Services.MyUserDetailServiceImp;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {
    private final MyUserDetailServiceImp myUserDetailServiceImp;
    private final JwtAuthFilter jwtAuthFilter;
    private final AppUserRepo appUserRepo;
    private final AuthUtil authUtil;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req->
                        req.requestMatchers("/public/**","/auth/**","/oauth2/**").permitAll()
                                //.requestMatchers("admin/**").hasRole("ADMIN")
                                //.requestMatchers("/doctor/**").hasAnyRole("DOCTOR","ADMIN")
                                .anyRequest().authenticated()


                )
                .oauth2Login(oauth->
                        oauth.successHandler(((request, response, authentication) -> {
                            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                            AppUser existingAppUser =  appUserRepo.findByUsername(oAuth2User.getAttribute("name")).orElse(null);
                            if(existingAppUser==null){
                                AppUser newAppUser =  AppUser.builder()
                                        .username(oAuth2User.getAttribute("name"))
                                        .password(oAuth2User.getAttribute("name"))
                                        .build();
                                AppUser savedAppUser =  appUserRepo.save(newAppUser);
                                String jwtToken = authUtil.getAccessToken(savedAppUser);
                                response.sendRedirect("http://localhost:5173/oauth-success?token=" + jwtToken);
                            } else{
                                String jwtToken = authUtil.getAccessToken(existingAppUser);
                                response.sendRedirect("http://localhost:5173/oauth-success?token=" + jwtToken);
                            }
                        }))
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                //.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
