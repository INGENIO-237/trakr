package app.vercel.ingenio_theta.trakr.auth;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    private final int TOKEN_EXPIRATION_TIME = 1000 * 60 * 30;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private JwtBuilder buildToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(getKey(), SignatureAlgorithm.HS256);
    }

    public String generateToken(String subject) {
        return buildToken(subject).compact();
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        return buildToken(subject)
                .setClaims(claims)
                .compact();
    }
}
