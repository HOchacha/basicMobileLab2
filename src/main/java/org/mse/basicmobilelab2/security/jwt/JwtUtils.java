package org.mse.basicmobilelab2.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mse.basicmobilelab2.exception.InvalidJwtTokenException;
import org.mse.basicmobilelab2.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${secretKey}")
    private String jwtSecret;

    @Value("${expirationTime}")
    private int jwtExpirationMs;


    public String generateJwtToken(Authentication authentication) {
        log.info(authentication);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        log.info(userPrincipal);
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public void validateJwtToken(String authToken) {
        try {
            log.info(authToken);
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new InvalidJwtTokenException("Invalid Jwt Token");
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new InvalidJwtTokenException("Jwt Token is expired");
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new InvalidJwtTokenException("Jwt token is unsupported");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new InvalidJwtTokenException("Jwt claims string is empty");
        }
    }

        public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
