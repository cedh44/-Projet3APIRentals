package com.openclassrooms.rentals.service;

import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder){
        this.encoder = jwtEncoder;
        this.decoder = jwtDecoder;
    }


    public String generateToken(String authenticationEmail){
        Instant now = Instant.now();
        /* String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" ")); */
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(8, ChronoUnit.HOURS)) //Token expires in 8 hours
                .subject(authenticationEmail)
                //.claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    //Extract email from token
    public String getEmailFromToken(String token){
        token = deleteBearerFromToken(token); //Delete "Bearer" from token
        if (token != null) {
            Jwt claims = this.decoder.decode(token);
            return claims.getSubject();
        } else {
            return "";
        }
    }

    //Delete "Bearer" from token
    public String deleteBearerFromToken(String token) {
        if (token != null) {
            if(token.startsWith("Bearer ")) return token.substring(7);
        }
        return null;
    }
}
