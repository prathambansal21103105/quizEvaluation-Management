package com.quiz.layoutPDF.Config;
import com.quiz.layoutPDF.models.Role;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
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
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, Role role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(key)
                .build();

        return jwtParser.parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Role extractRole(String token) {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(key)
                .build();

        String role = jwtParser.parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);

        if (role == null) {
            throw new IllegalArgumentException("JWT does not contain a role claim!");
        }
        return Role.valueOf(role);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
