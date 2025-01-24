package com.infominez.catalog.auth.service;

import com.infominez.catalog.auth.base.BaseResponse;
import com.infominez.catalog.auth.utils.Constants;
import com.infominez.catalog.auth.wrapper.AuthResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Slf4j
@Service
public class JwtTokenService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private final Set<String> blacklistedTokens = new HashSet<>();

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }


    public AuthResponse generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private AuthResponse createToken(Map<String, Object> claims, String userName) {
        String jwtId = UUID.randomUUID().toString();
        Date issueDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + 1000 * 60 * 360);
        String token =  Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userName)
                        .setId(jwtId) // Set JWT ID
                        .setIssuedAt(issueDate)
                        .setExpiration(expiryDate)
                        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
        return new AuthResponse(token, issueDate, expiryDate);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public BaseResponse invalidateToken(String token) {
        String[] tokens = token.split(",");
        log.info("Invalidating token : {}", tokens[0]);
        blacklistedTokens.add(tokens[0]);
        return new BaseResponse().set(200, Constants.LOGGED_OUT_SUCCESSFULLY);
    }

    public boolean isTokenBlacklisted(String token) {
        log.info("isTokenBlacklisted : {}", token);
        return blacklistedTokens.contains(token);
    }

}
