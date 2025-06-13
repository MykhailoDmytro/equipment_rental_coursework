package ua.opnu.equipment_rental.Security.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication; // <-- цей імпорт!
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthTokenGenerator {

    private final String secret = "tutun228_tutun228_tutun228_tutun228";
    private final long secretExpiryTime = 3600 * 1000;

    public String tokenGenerator(Authentication authentication) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + secretExpiryTime);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
