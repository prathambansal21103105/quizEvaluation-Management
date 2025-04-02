package com.quiz.layoutPDF.models;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "qYjD1/t/BbQ3yJGiY9P+2hXNk6dUeYpTATrR8Y/Q0T4=";
    private final SecretKey key;

    public JwtUtil() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes); // Secure key
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key) // No need for explicit algorithm
                .compact();
    }

    public String extractEmail(String token) {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(key) // Use verifyWith() instead of setSigningKey()
                .build();

        return jwtParser.parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key) // Use verifyWith()
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
