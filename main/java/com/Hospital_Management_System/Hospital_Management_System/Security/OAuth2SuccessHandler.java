package com.Hospital_Management_System.Hospital_Management_System.Security;

import com.Hospital_Management_System.Hospital_Management_System.Dto.LoginResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@AllArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final AuthService authService;
    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token =  (OAuth2AuthenticationToken) authentication;
        String registrationId = token.getAuthorizedClientRegistrationId();
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        ResponseEntity<LoginResponseDto> newResponse = authService.handleOAuthLogin(oAuth2User,registrationId);
//        response.setStatus(newResponse.getStatusCode().value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.getWriter().write(objectMapper.writeValueAsString(newResponse.getBody()));
        response.sendRedirect(
                "http://localhost:5173/oauthsuccess" +
                        "?token=" + newResponse.getBody().getJwt() +
                        "&id=" + newResponse.getBody().getId() +
                        "&username=" + newResponse.getBody().getUsername()
        );
    }
}
