package app.butakane.backend.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms:86400000}") // 24 hours default
    private long expirationMs;

    public String generateToken(String userId, String username) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserId(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject(); // userId
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }

    public String extractUsername(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.get("username", String.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}

