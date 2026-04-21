package org.example.psychology_center.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {


        @Value("${jwt.secret}")
        private String secretKey;

        @Value("${jwt.expiration}")
        private long expirationTime;

        // TOKEN GENERATE
        public String generateToken(String username, Role role) {

            Map<String, Object> claims = new HashMap<>();
            claims.put("role", role); // tokenin içinə role əlavə edirik

            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        // USERNAME EXTRACT
        public String extractUsername(String token) {
            return extractAllClaims(token).getSubject();
        }

        // VALIDATION
        public boolean isTokenValid(String token, UserDetails userDetails) {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        // CHECK EXPIRE
        private boolean isTokenExpired(String token) {
            return extractAllClaims(token).getExpiration().before(new Date());
        }

        // CLAIMS
        private Claims extractAllClaims(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

        // SECRET KEY
        private Key getSignKey() {
            byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(secretKey.getBytes()));
            return Keys.hmacShaKeyFor(keyBytes);
        }


}
