package com.demo.project.nitin.ecommerce.utils;

import com.demo.project.nitin.ecommerce.configuration.ApplicationConfig;
import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.enums.JwtTokenType;
import com.demo.project.nitin.ecommerce.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {

    private final ApplicationConfig applicationConfig;

    public String generateToken(User user, UUID id, JwtTokenType type) {
        Map<String, Object> claims = new HashMap<>();
        long expirationTime=applicationConfig.getJwtProperties().getAccessTokenExpirationTime();
        if(type.equals(JwtTokenType.REFRESH)) {
            expirationTime = applicationConfig.getJwtProperties().getRefreshTokenExpirationTime();
        }
        return createToken(claims, user, expirationTime,id);
    }
    public String createToken(Map<String, Object> claims, User user,long expiration,UUID id) {
        claims.put(Constants.Security.ROLES,user.getRoles().stream().map(r -> r.getAuthority().getValue()).toList());
        return Jwts.builder()
                .setIssuer(applicationConfig.getJwtProperties().getIssuer())
                .setSubject(user.getEmail())
                .addClaims(claims)
                .setId((id != null) ?id.toString() : UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(
                        applicationConfig.getJwtProperties().getSecretKey().getBytes(StandardCharsets.UTF_8))
                )
                .compact();
    }

    public Claims extractallclaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(
                        applicationConfig.getJwtProperties()
                                .getSecretKey().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
