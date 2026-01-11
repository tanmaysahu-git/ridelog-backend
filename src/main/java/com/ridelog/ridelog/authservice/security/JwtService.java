package com.ridelog.ridelog.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    public static final String ACCESS = "ACCESS";
    public static final String REFRESH = "REFRESH";
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-token-expiry}")
    private long accessTokenExpiry;
    @Value("${jwt.refresh-token-expiry}")
    private long refreshTokenExpiry;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserDetails user) {
        return buildToken(user.getUsername().toString(), JwtService.ACCESS, accessTokenExpiry);
    }

    public String generateRefreshToken(UserDetails user) {
        return buildToken(user.getUsername().toString(), JwtService.REFRESH, refreshTokenExpiry);
    }

    private String buildToken(String subject, String type, long expiry) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expiry);

        return Jwts.builder()
                .setSubject(subject)
                .claim("type", type)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isRefreshToken(String token) {
        return REFRESH.equals(extractClaims(token).get("type", String.class));
    }

    public boolean isAccessToken(String token) {
        return ACCESS.equals(extractClaims(token).get("type", String.class));
    }

    //todo in case of custom claims
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .claims(claims)
//                .subject(subject)
//                .header().empty().add("typ","JWT")
//                .and()
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 5 minutes expiration time
//                .signWith(getSigningKey())
//                .compact();
//    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getAccessTokenExpiry() {
        return accessTokenExpiry / 1000;
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}