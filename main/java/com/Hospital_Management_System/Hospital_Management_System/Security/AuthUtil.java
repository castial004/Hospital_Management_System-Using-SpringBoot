package com.Hospital_Management_System.Hospital_Management_System.Security;

import com.Hospital_Management_System.Hospital_Management_System.Entity.AppUser;
import com.Hospital_Management_System.Hospital_Management_System.Entity.Enums.AuthProviderType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class AuthUtil {
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;
    private final long EXPIRATION_MS = 10 * 60 * 1000; // 1000 ms = 1sec-> 1*60=60sec->1min*10=>10mins
    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getAccessToken(AppUser appUser){
        Date now = new Date();
        Date expireyDate = new Date(now.getTime() + EXPIRATION_MS);
        return Jwts.builder()
                .subject(appUser.getUsername())
                .claim("userId",appUser.getId())
                .issuedAt(now)
                .expiration(expireyDate)
                .signWith(getSecretKey())
                .compact();
    }
    public String getUserNameFromToken(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public AuthProviderType getAuthProviderFromRegistrationId(String id){
        switch (id.toLowerCase()){
            case "google":
                return AuthProviderType.GOOGLE;
            case "github":
                return AuthProviderType.GITHUB;
            case "email":
                return AuthProviderType.EMAIL;
            default:
                throw new IllegalArgumentException("Unsupported oauth2 provider: "+id);
        }
    }

    public String getProviderId(OAuth2User oAuth2User, String registrationId) {
            String providerId =  switch(registrationId.toLowerCase()){
                    case "google"-> oAuth2User.getAttribute("sub");
                    case "github"-> oAuth2User.getAttribute("id").toString();
                    default->{
                        log.error("unsupported oauth2 provider: ",registrationId);
                        throw new IllegalArgumentException();
                    }
            };
            if (providerId == null || providerId.isBlank()){
                log.error("unable to determine provider id for provider: "+registrationId);
                throw new IllegalArgumentException("unable to determine provider id for OAuth2 login");
            }
            return providerId;
    }

    public String determineUserNameFromOAuth2Login(OAuth2User oAuth2User,String providerId,String registerationId){
        String email = oAuth2User.getAttribute("email");
        if(email!=null && !email.isBlank()){
            return email;
        }
        return switch (registerationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }
}
