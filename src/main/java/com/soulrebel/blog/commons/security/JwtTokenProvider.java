package com.soulrebel.blog.commons.security;

import com.soulrebel.blog.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public String generateJwtToken(Authentication authentication) {
        final String username = authentication.getName ();
        final Date currentDate = new Date ();
        final Date expiryDate = new Date (currentDate.getTime () + jwtExpirationDate);
        return Jwts.builder ()
                .setSubject (username)
                .setIssuedAt (currentDate)
                .setExpiration (expiryDate)
                .signWith (key ())
                .compact ();
    }

    private Key key() {
        return Keys.hmacShaKeyFor (Decoders.BASE64.decode (jwtSecret));
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder ()
                .setSigningKey (key ())
                .build ()
                .parseClaimsJws (token)
                .getBody ()
                .getSubject ();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder ()
                    .setSigningKey (key ())
                    .build ()
                    .parseClaimsJws (authToken);

            return true;
        } catch (MalformedJwtException e) {
            throw new BlogAPIException (HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new BlogAPIException (HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new BlogAPIException (HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new BlogAPIException (HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }
}
