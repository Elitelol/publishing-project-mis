package com.aivarasnakvosas.publishingservicemis.security.JWT;

import com.aivarasnakvosas.publishingservicemis.config.properties.SecurityProperties;
import com.aivarasnakvosas.publishingservicemis.security.UserContext;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Aivaras Nakvosas
 */
@Component
public class JWTUtils {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    @Autowired
    private SecurityProperties securityProperties;

    public String generateJwtToken(Authentication authentication) {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userContext.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + securityProperties.getJwtExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, securityProperties.getJwtTokenSecret())
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(securityProperties.getJwtTokenSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(securityProperties.getJwtTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
