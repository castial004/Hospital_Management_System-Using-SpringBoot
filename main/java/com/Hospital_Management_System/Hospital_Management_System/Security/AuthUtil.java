package com.Hospital_Management_System.Hospital_Management_System.Security;

import com.Hospital_Management_System.Hospital_Management_System.Entity.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
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
}
