package org.yunusgedik.user.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Service
public class JwtService {

    @Value("${jwt.expiration-minutes}")
    private long expirationMinutes;

    private final JwtKeyProvider keyProvider;

    public JwtService(JwtKeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    public String generateToken(Long userId, Set<String> roles) {
        Instant now = Instant.now();
        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusSeconds(expirationMinutes * 60)))
            .addClaims(Map.of("roles", roles)) // add roles claim
            .signWith(keyProvider.getPrivateKey(), SignatureAlgorithm.RS256)
            .compact();
    }
}